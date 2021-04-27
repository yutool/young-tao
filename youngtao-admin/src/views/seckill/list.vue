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
                <img :src="scope.row.image" width="40">
              </template>
            </el-table-column>
            <el-table-column label="现价" prop="price"></el-table-column>
            <el-table-column label="原价" prop="oldPrice"></el-table-column>
            <el-table-column label="活动数量" prop="num"></el-table-column>
            <el-table-column label="剩余数量" prop="residue"></el-table-column>
            <el-table-column label="活动开始时间" min-width="100">
              <template slot-scope="scope">
                <span>{{scope.row.startTime}}点</span>
              </template>
            </el-table-column>
            <el-table-column label="审核转态">
              <template slot-scope="scope">
                <span v-if="scope.row.status == 0">审核中</span>
                <span v-if="scope.row.status == 1">审核通过</span>
                <span v-if="scope.row.status == 2">审核失败</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" min-width="115">
              <template slot-scope="scope">
                <el-button class="mr-1" type="primary" size="mini" @click="editRow(scope.row)">编辑</el-button>
                <template>
                  <el-popconfirm title="是否确认删除？" @onConfirm="deleteRow(scope.row)">
                    <el-button slot="reference" type="danger" size="mini">删除</el-button>
                  </el-popconfirm>
                </template>
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
      <el-table-column label="活动数量" prop="skuList.length"></el-table-column>
      <el-table-column label="是否上架">
        <template slot-scope="scope">
          <span v-if="scope.row.isMarketable == 0">未上架</span>
          <span v-if="scope.row.isMarketable == 1">已上架</span>
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

    <!-- 修改信息弹窗 -->
    <el-dialog title="修改活动商品" :visible.sync="editDialog" width="600px">
      <edit-form :data="editData" @confirm="confirmEdit" />
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { getMerchantProduct } from '@/api/gsc/product';
import { deleteById } from '@/api/gsc/sku';
import EditForm from './components/EditForm.vue'

@Component({
  components: {
    EditForm
  }
})
export default class GoodsList extends Vue {
  private spuStatus = ['审核通过', '审核中', '审核失败']
  private pageInfo: any = { pageSize: 10 }  // 初始化页面大小
  private productList: any = []
  // 修改弹窗
  private editData = {}
  private editDialog = false;

  // 修改数据
  private editRow(data: any) {
    this.editData = data;
    this.editDialog = true;
  }
  private confirmEdit() {
    this.editDialog = false;
    this.getProduct(this.pageInfo.pageNum)
  }

  // 删除数据
  private deleteRow(data: any) {
    deleteById({id: data.skuId}).then((res: any) => {
      this.$message({type: 'success', message: '删除成功'})
      this.getProduct(this.pageInfo.pageNum)
    })
  }
 
  // 切换页面
  private getProduct(page = 1, size = this.pageInfo.pageSize) {
    getMerchantProduct({page, size}).then((res: any) => {
      const { data } = res
      this.pageInfo = data
      this.productList = data.list
    })
  }
  // 切换显示条数
  private handleSizeChange(size: any) {
    this.getProduct(1, size)
  }
  
  private mounted() {
    this.getProduct()
  }
}
</script>

<style scoped lang="scss">

</style>
