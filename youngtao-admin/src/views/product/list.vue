<template>
  <div class="container">
    <!-- 数据 -->
    <el-table :data="productList">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table :data="props.row.skuList">
            <el-table-column label="skuId" prop="skuId"></el-table-column>
            <el-table-column label="图片" prop="skuId">
              <template slot-scope="scope">
                <img :src="scope.row.images[0]" width="40">
              </template>
            </el-table-column>
            <el-table-column label="型号" min-width="150">
              <template slot-scope="scope">
                <span>{{ Object.values(scope.row.sku).join(", ") }}</span>
              </template>
            </el-table-column>
            <el-table-column label="现价" prop="price"></el-table-column>
            <el-table-column label="原价" prop="oldPrice"></el-table-column>
            <el-table-column label="库存" prop="num"></el-table-column>
            <el-table-column label="销量" prop="saleNum"></el-table-column>
            <el-table-column label="操作" min-width="160">
              <template slot-scope="scope">
                <el-button type="info" size="mini" @click="editSku(scope.row)">编辑</el-button>
                <el-button class="ml-1" type="primary" size="mini" @click="addSeckill(props.row, scope.row)">活动</el-button>
                <el-popconfirm class="ml-1" title="是否确认删除？" @onConfirm="deleteSku(scope.row)">
                  <el-button slot="reference" type="danger" size="mini">删除</el-button>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column label="spuId" prop="spuId"></el-table-column>
      <el-table-column label="封面">
        <template slot-scope="scope">
          <img :src="scope.row.images[0]" width="40">
        </template>
      </el-table-column>
      <el-table-column label="名称" prop="spu" min-width="100"></el-table-column>
      <el-table-column label="总销量" prop="saleNum"></el-table-column>
      <el-table-column label="总商品数" prop="skuList.length"></el-table-column>
      <el-table-column label="审核转态">
        <template slot-scope="scope">
          <span v-if="scope.row.status == 0">审核中</span>
          <span v-if="scope.row.status == 1">审核通过</span>
          <span v-if="scope.row.status == 2">审核失败</span>
        </template>
      </el-table-column>
      <el-table-column label="是否上架">
        <template slot-scope="scope">
          <span v-if="scope.row.isMarketable == 0">未上架</span>
          <span v-if="scope.row.isMarketable == 1">已上架</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="100px">
        <template slot-scope="scope">
          <el-button class="mr-1" type="warning" size="mini" @click="editSpu(scope.row)">编辑</el-button>
          <el-popconfirm title="是否确认删除？" @onConfirm="deleteSpu(scope.row)">
            <el-button slot="reference" type="danger" size="mini">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="block text-center mt-3">
      <el-pagination
        @current-change="getProduct"
        @size-change="handleSizeChange"
        :current-page="pageInfo.pageNum"
        :page-sizes="[10, 30, 50]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
      </el-pagination>
    </div>

    <!-- 添加活动商品 -->
    <el-dialog title="添加活动商品" :visible.sync="seckillDialog" width="600px">
      <seckill-form :data="seckillData" @confirm="seckillDialog = false" />
    </el-dialog>

    <!-- 编辑SPU -->
    <el-dialog title="添加活动商品" :visible.sync="spuDialog" width="600px">
      <edit-spu-form :data="spuData" @confirm="spuConfirm" />
    </el-dialog>

    <!-- 编辑SKU -->
    <el-dialog title="添加活动商品" :visible.sync="skuDialog" width="600px">
      <edit-sku-form :data="skuData" @confirm="skuConfirm" />
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { getMerchantProduct } from '@/api/gmc/product';
import { deleteSku } from '@/api/gmc/sku';
import { deleteSpu } from '@/api/gmc/spu';
import SeckillForm from './components/SeckillForm.vue';
import EditSkuForm from './components/EditSkuForm.vue';
import EditSpuForm from './components/EditSpuForm.vue';

@Component({
  components: {
    SeckillForm, EditSkuForm, EditSpuForm
  }
})
export default class ProductList extends Vue {
  private spuStatus = ['审核通过', '审核中', '审核失败']
  private pageInfo: any = { pageSize: 10 }  // 初始化页面大小
  private productList: any = []
  // 活动弹窗
  private seckillDialog = false;
  private seckillData = {};
  // spu弹窗
  private spuDialog = false;
  private spuData = {};
   // sku弹窗
  private skuDialog = false;
  private skuData = {};
 
  // 切换页面
  private getProduct(page = 1, size = this.pageInfo.pageSize) {
    getMerchantProduct({page, size}).then((res: any) => {
      const { data } = res
      this.pageInfo = data
      this.productList = data.list
    })
  }
  // 切换显示条数
  private handleSizeChange(size: number) {
    this.getProduct(1, size)
  }
  private mounted() {
    this.getProduct()
  }

  // 添加活动商品
  private addSeckill(spu: any, sku: any) {
    this.seckillData = {spu, sku};
    this.seckillDialog = true;
  }

  // 编辑spu
  private editSpu(data: any) {
    this.spuData = this.$utils.copyOf(data);
    this.spuDialog = true;
  }
  private spuConfirm() {
    this.spuDialog = false;
    this.getProduct(this.pageInfo.pageNum)
  }
  private deleteSpu(data: any) {
    deleteSpu({id: data.spuId, logic: true}).then((res: any) => {
      this.$message({type: 'success', message: '删除成功'})
      this.getProduct(this.pageInfo.pageNum)
    })
  }

  // 编辑sku
  private editSku(data: any) {
    this.skuData = this.$utils.copyOf(data);
    this.skuDialog = true;
  }
  private skuConfirm() {
    this.skuDialog = false;
    this.getProduct(this.pageInfo.pageNum)
  }
  private deleteSku(data: any) {
    deleteSku({id: data.skuId}).then((res: any) => {
      this.$message({type: 'success', message: '删除成功'})
      this.getProduct(this.pageInfo.pageNum)
    })
  }
}
</script>

<style scoped lang="scss">

</style>
