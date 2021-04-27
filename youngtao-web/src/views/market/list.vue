<template>
  <div class="container">
    <!-- 导航栏 -->
    <div class="nav-wrap">
      <el-popover class="nav-menu mt-2" placement="bottom-start" trigger="hover">
        <el-cascader-panel v-model="menuIds" class="cascader-menu-500" :options="menuList" @change="selectedMenu"
          :props="{label: 'name', value: 'categoryId', expandTrigger: 'hover'}">
        </el-cascader-panel>
        <span slot="reference" style="font-size: 22px">
          <i class="el-icon-s-operation"></i>
          <span>菜单</span>
          <span v-if="menuIds[2]" class="ml-2" style="font-size: 14px">
            当前选中：{{menuIds[2]}}
            <i class="el-icon-circle-close pointer" @click="clearCategory"></i>
          </span>
        </span>
      </el-popover>
      <el-input placeholder="按名称搜索" v-model="searchValue" class="input-with-select w-50 mt-2">
        <el-button slot="append" icon="el-icon-search" @click="queryProduct"></el-button>
      </el-input>
      <!-- 快捷按钮 -->
      <ul class="nav-list">
        <li>
          <i class="el-icon-chat-dot-round"></i>
          <div>消息</div>
        </li>
        <li>
          <i class="el-icon-star-off"></i>
          <div>收藏</div>
        </li>
        <li>
          <i class="el-icon-shopping-cart-full"></i>
          <div>购物车</div>
        </li>
      </ul>
    </div>

    <!-- 商品 -->
    <div class="row">
      <div class="m-col" v-for="product in productList" :key="product.spuId">
        <product-card :spu="product" />
      </div>
    </div>

    <!-- 分页 -->
      <el-pagination
        class="text-center mt-5"
        @current-change="searchProduct"
        @size-change="handleSizeChange"
        :current-page="pageInfo.pageNum"
        :page-sizes="[1, 10, 20, 30, 50]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
      </el-pagination>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import ProductCard from '@/components/ProductCard.vue'
import { getCategory } from '@/api/gmc/category'
import { searchProduct } from '@/api/gmc/product'

@Component({
  components: {
    ProductCard
  }
})
export default class GoodsList extends Vue {
  private pageInfo: any = { pageSize: 20 }
  private productList = []
  private menuList = []
  private menuIds = new Array(3)
  private searchValue = ''

  // 切换显示条数
  private handleSizeChange(size: number) {
    this.searchProduct(1, size)
  }
  private searchProduct(page = 1, size = this.pageInfo.pageSize) {
    searchProduct( {category: this.menuIds[2], searchValue: this.searchValue, page, size})
    .then((res: any) => {
      this.pageInfo = res.data
      this.productList = res.data.list
    })
  }
  // 目录
  private getCategory() {
    getCategory().then((res: any) => {
      this.menuList = res.data
    })
  }
  private mounted() {
    this.getCategory()
    this.searchProduct()
  }

  // 查询
  private queryProduct() {
    this.searchProduct()
  }

  // 菜单
  private selectedMenu() {
    this.searchValue = ''
    this.searchProduct()
  }
  private clearCategory() {
    for (let i = 0; i < this.menuIds.length; i++) {
      this.menuIds[i] = null;
    }
    this.selectedMenu()
  }
}
</script>

<style scoped lang="scss">
.nav-wrap {
  display: flex;
  padding-top: 10px;
  .nav-menu {
    flex: 1;
  }
  .nav-list {
    list-style: none;
    li {
      display: inline-block;
      padding: 5px;
      margin-left: 10px;
      text-align: center;
      cursor: pointer;
    }
    i {
      font-size: 22px;
    }
    div {
      font-size: 14px;
    }
  }
}
</style>