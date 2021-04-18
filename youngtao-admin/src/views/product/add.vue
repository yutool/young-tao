<template>
  <div class="container">
    <el-form ref="spuForm" class="pt-3" :model="spuForm" :rules="rules" label-width="100px">
      <el-form-item label="产品名称：" prop="spu">
        <el-input v-model="spuForm.spu"></el-input>
      </el-form-item>
      <el-form-item label="所属目录：" prop="category1Id">
        <el-input :value="selectedCategory.map(item => item.name).join(' / ')" disabled>
          <template slot="append">
            <el-button type="primary" @click="openCategoryDialog">选择菜单</el-button>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="产品相册：" prop="images">
        <el-upload action="" :limit="3" :file-list="spuForm.images" :on-remove="handleRemove" list-type="picture" style="line-height: 0">
        </el-upload>
        <el-button class="mt-3" size="small" type="primary" @click="openImageDialog(spuForm.images)">点击上传</el-button>
      </el-form-item>
      <el-form-item label="商品详情：">
        <!-- 数据 -->
        <table class="table text-center">
          <thead>
            <tr> 
              <th scope="col">#</th>
              <th v-for="(value, name) in spuForm.skuTemplate" :key="name">{{name}}</th>
              <th scope="col">现价（￥）</th>
              <th scope="col">原价（￥）</th>
              <th scope="col">商品库存</th>
              <th scope="col">库存预警值</th>
              <th scope="col">图片</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in skuList" :key="index">
              <th scope="col">{{ index+1 }}</th>
              <td v-for="val in item.sku" :key="val"> {{val}} </td>
              <td> <input type="text" v-model="item.price" class="simple-input" style="width: 60px"> </td>
              <td> <input type="text" v-model="item.oldPrice" class="simple-input" style="width: 60px"> </td>
              <td> <input type="text" v-model="item.num" class="simple-input" style="width: 60px"> </td>
              <td> <input type="text" v-model="item.alertNum" class="simple-input" style="width: 60px"> </td>
              <td style="display: flex;">
                <el-upload action="" :limit="3" :file-list="item.images" :on-remove="handleRemove" class="sku-img">
                </el-upload>
                <el-button class="ml-3" size="small" type="primary" @click="openImageDialog(item.images)" style="align-self: flex-start;">点击上传</el-button>
              </td>
            </tr>
          </tbody>
        </table>
      </el-form-item>
      <el-form-item label="属性模板：" prop="skuTemplate">
        <el-form ref="form" label-width="100px" class="mb-3">
          <el-form-item v-for="(value, name) in spuForm.skuTemplate" :key="name"  :label="name + '：'">
            <el-checkbox-group v-model="skuArr[name]" >
              <el-checkbox v-for="(item, index) in value" :key="item" @change="generateSku" :label="item">
                {{item}} <i @click="delAttrItem(name, index)" class="el-icon-circle-close text-dark ml-2"></i>
              </el-checkbox>
            </el-checkbox-group>
            <div>
              <input type="text" class="simple-input" /> 
              <el-button class="ml-3" size="mini" @click="addAttrItem(name, $event)">添加属性</el-button>
              <el-button size="mini" @click="delAttr(name)">删除属性分类</el-button>
            </div>
          </el-form-item>
          <el-form-item label="添加属性分类" class="mt-3" label-width="100px">
            <input type="text" class="simple-input" /> 
            <el-button class="ml-3" type="primary" size="mini" @click="addAttr">添加</el-button>
          </el-form-item>
        </el-form>
      </el-form-item>
      <el-form-item label="是否上架：">
        <el-radio-group v-model="spuForm.isMarketable" class="mt-2">
          <el-radio :label="true">上架</el-radio>
          <el-radio :label="false">下架</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addProduct">添加商品</el-button>
      </el-form-item>
    </el-form>

    <!-- 选择目录弹窗 -->
    <el-dialog title="菜单选择" :visible.sync="categoryDialog" width="500px" >
      <el-select v-model="tmpSelected[0]" filterable placeholder="一级菜单" class="w-100 mb-3" @change="category1Change">
        <el-option v-for="(item, idx) in category1List" :key="item.value" :label="item.name" :value="idx">
        </el-option>
      </el-select>
      <el-select v-model="tmpSelected[1]" filterable placeholder="二级菜单" class="w-100 mb-3" @change="category2Change">
        <el-option v-for="(item, idx) in category2List" :key="item.value" :label="item.name" :value="idx">
        </el-option>
      </el-select>
      <el-select v-model="tmpSelected[2]" filterable placeholder="三级菜单" class="w-100">
        <el-option v-for="(item, idx) in category3List" :key="item.value" :label="item.name" :value="idx">
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="categoryDialog = false">取 消</el-button>
        <el-button type="primary" @click="handleSelector" :disabled="tmpSelected[2] == null">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 添加图片 -->
    <el-dialog title="新增图片" :visible.sync="imageDialog" width="500px">
      <el-input v-model="imageUrl"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="categoryDialog = false">取 消</el-button>
        <el-button type="primary" @click="handleUpload" :disabled="!imageUrl">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { Component, Ref, Vue } from 'vue-property-decorator';
import { getSubmenu } from '@/api/gmc/category';
import { addProduct } from '@/api/gmc/product';

@Component
export default class AddGoods extends Vue {
  @Ref('spuForm') private refSpuForm: any
  private spuForm: any = {
    spu: '',
    category1Id: '',
    category2Id: '',
    category3Id: '',
    isMarketable: true,
    skuTemplate: {},
    images: []
  }
  private skuList: any = []

  // 目录相关
  private categoryDialog = false;
  private tmpSelected = new Array(3);
  private selectedCategory = new Array(3);
  private category1List: any = [];
  private category2List: any = [];
  private category3List: any = [];

  // 图片相关
  private imageUrl = ''
  private imageDialog = false;
  private currentList: any = [];

  // 商品相关
  private skuArr: any = {}      // 被选中的sku

  private rules = {
    spu: [
      { required: true, message: '请输入产品名称', trigger: 'blur' },
    ],
    category1Id: [
      { required: true, message: '请选择所属目录', trigger: 'blur' },
    ],
    images: [
      { required: true, message: '请上传商品图片', trigger: 'blur' },
    ],
    skuTemplate: [
      { required: true, message: '请选择商品属性', trigger: 'blur' },
    ],
  }
  // 提交表单
  private addProduct() {
    this.refSpuForm.validate((valid: any) => {
      if (valid) {
        const productData = this.$utils.copyOf(this.spuForm)
        productData.images = productData.images.map((item: any) => item.url)
        productData.skuList = []
        for (const sku of this.skuList) {
          const skuData = this.$utils.copyOf(sku)
          skuData.images = skuData.images.map((item: any) => item.url)
          productData.skuList.push(skuData)
        }
        addProduct(productData).then((res: any) => {
          this.$message({type: 'success', message: '添加商品成功'})
          this.$router.go(0)
        })
      }
    })
  }

  // 选择目录
  private openCategoryDialog() {
    this.categoryDialog = true;
    this.tmpSelected = new Array(3)
    getSubmenu('0').then((res: any) => {
      this.category1List = res.data
    })
  }
  private category1Change() {
    this.tmpSelected[1] = null;
    this.tmpSelected[2] = null;
    getSubmenu(this.category1List[this.tmpSelected[0]].categoryId).then((res: any) => {
      this.category2List = res.data
    })
  }
  private category2Change() {
    this.tmpSelected[2] = null;
    getSubmenu(this.category2List[this.tmpSelected[1]].categoryId).then((res: any) => {
      this.category3List = res.data
    })
  }
  private handleSelector() {
    this.categoryDialog = false;
    this.selectedCategory[0] = this.category1List[this.tmpSelected[0]];
    this.selectedCategory[1] = this.category2List[this.tmpSelected[1]];
    this.selectedCategory[2] = this.category3List[this.tmpSelected[2]];
    this.spuForm.category1Id = this.selectedCategory[0].categoryId
    this.spuForm.category2Id = this.selectedCategory[1].categoryId
    this.spuForm.category3Id = this.selectedCategory[2].categoryId
  }

  // 图片相关
  private openImageDialog(data: any) {
    this.imageDialog = true;
    this.imageUrl = ''
    this.currentList = data
  }
  private handleUpload() {
    this.imageDialog = false;
    this.currentList.push({name: '图片', url: this.imageUrl})
  }
  private handleRemove(file: any, fileList: any) {
    for (let i = 0; i < this.currentList.length; i++) {
      if (this.currentList[i].uid === file.uid) {
        this.currentList.splice(i, 1)
      }
    }
  }
  
  // ------ 添加属性 ------
  private addAttr(e: any) {
    const inputValue = $(e.currentTarget).prev().val()
    $(e.currentTarget).prev().val('')
    if (inputValue === '' || this.spuForm.skuTemplate.hasOwnProperty(inputValue)) {
      return
    }
    Vue.set(this.spuForm.skuTemplate, inputValue, [])
    Vue.set(this.skuArr, inputValue, [])
  }
  private delAttr(name: any) {
    Vue.delete(this.spuForm.skuTemplate, name)
    Vue.delete(this.skuArr, name)
  }
  private addAttrItem(name: string, e: any) {
    const inputValue = $(e.currentTarget).prev().val()
    $(e.currentTarget).prev().val('')
    if (inputValue === '' || this.spuForm.skuTemplate[name].indexOf(inputValue) !== -1) {
      return
    }
    this.spuForm.skuTemplate[name].push(inputValue)
  }
  private delAttrItem(name: string, index: number) {
    this.spuForm.skuTemplate[name].splice(index, 1)
  }
  
  // 生成sku
  private generateSku() {
    const flagArr: any = []   // 临时栈，存放属性名
    const resultArr: any = [] // 属性值数组
    this.skuList.length = 0
    this.generateDfs(flagArr, resultArr)
  }
  private generateDfs(flagArr: any, resultArr: any) {
    const keys = Object.keys(this.skuArr)
    if (resultArr.length === keys.length) {
      const skuItem: any = { sku: {}, price: '', oldPrice: '',  num: '', alertNum: '', images: [] }
      const skuJson: any = {}
      // sku数组转成Json对象
      for (let i = 0; i < keys.length; i++) {
        skuJson[keys[i]] = resultArr[i]
      }
      skuItem.sku = skuJson
      this.skuList.push(skuItem)
    }
    for (const key of keys) {
      // key不存在且key只能顺序添加
      if (flagArr.indexOf(key) === -1 && flagArr.length === keys.indexOf(key)) {
        flagArr.push(key)
        for (const item of this.skuArr[key]) { // 一行
          resultArr.push(item)
          this.generateDfs(flagArr, resultArr)
          resultArr.pop()
        }
        flagArr.pop(key)
      }
    }
  }
}
</script>

<style scoped lang="scss">
.simple-input {
  height: 30px;
  border: none;
  border-bottom: 1px solid #999;
  outline: none;
}
.sku-img {
  line-height: 0;
  flex: 1;
  width: 20px;
}
</style>
