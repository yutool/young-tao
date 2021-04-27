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
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="recycleSpu(scope.row)">恢复</el-button>
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
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { getMerchantProduct } from '@/api/gmc/product';
import { recycleSpu } from '@/api/gmc/spu';

@Component
export default class GoodsList extends Vue {
  private spuStatus = ['审核通过', '审核中', '审核失败']
  private pageInfo: any = { pageSize: 10 }  // 初始化页面大小
  private productList: any = []
 
  // 切换页面
  private getProduct(page = 1, size = this.pageInfo.pageSize) {
    getMerchantProduct({page, size, deleted: true}).then((res: any) => {
      const { data } = res
      this.pageInfo = data
      this.productList = data.list
    })
  }
  private handleSizeChange(size: number) {
    this.getProduct(1, size)
  }
  private mounted() {
    this.getProduct()
  }

  // 回收商品
  private recycleSpu(data: any) {
    recycleSpu({id: data.spuId}).then((res: any) => {
      this.$message({type: 'success', message: '商品回收成功'})
      this.getProduct(this.pageInfo.pageNum)
    })
  }
}
</script>

<style scoped lang="scss">

</style>
