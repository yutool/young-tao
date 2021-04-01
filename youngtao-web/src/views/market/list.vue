<template>
  <div>
    <div class="banner-wrap">
      <!-- 轮播图 -->
      <div class="slideshow">
        <el-carousel trigger="click" height="450px">
          <el-carousel-item>
            <a href="#"> <img src="@/assets/banner2.jpg" alt=""> </a>
          </el-carousel-item>
          <el-carousel-item>
            <a href="#"> <img src="@/assets/banner3.jpg" alt=""> </a>
          </el-carousel-item>
          <el-carousel-item>
            <a href="#"> <img src="@/assets/banner1.jpg" alt=""> </a>
          </el-carousel-item>
        </el-carousel>
      </div>
      <!-- 菜单 -->
      <div v-if="rootMenu.length != 0" class="category">
        <ul class="category-list">
          <li v-for="i in 15" :key="i">
            {{ rootMenu[i-1].name }}
          </li>
        </ul>
      </div>
    </div>
    <!-- 商品列表 -->
    <div class="container">
      <div v-for="menu in recommendMenu" :key="menu.cateogryId">
        <!-- 推荐菜单 -->
        <div class="content-menu mt-3">
          <div class="sideIcon" style="background-color: #FFA1B8"></div>
          <ul>
            <li>{{menu.name}}</li>
            <li v-for="item in menu.children" :key="item.cateogyrId">
              {{ item.name }}
            </li>
          </ul>
        </div>
        <!-- 商品 -->
        <div class="row">
          <div class="m-col" v-for="product in productMap" :key="product.spuId">
            <product-card :spu="product" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import ProductCard from './components/ProductCard.vue'
import { getSpuList } from '@/api/gmc/spu'
import { getMenu, getRecommendMenu } from '@/api/gmc/category'
import { listRecommendProduct } from '@/api/gmc/product'

@Component({
  components: {
    ProductCard
  }
})
export default class GoodsList extends Vue {
  private rootMenu = []
  private recommendMenu: any = {}
  private productMap = {}

  private mounted() {
    this.getMenu()
    this.getProduct()
  }
  // 获取
  private getMenu() {
    getMenu().then((res: any) => {
      this.rootMenu = res.data
      this.$log.info('获取菜单', res)
    })
  }
  // 获取首页内容
  private getProduct() {
    getRecommendMenu().then((res: any) => {
      this.recommendMenu = res.data
      this.$log.info('获取推荐菜单', this.recommendMenu)
      this.listRecommendProduct(1, 10);
    })
  }
  private listRecommendProduct(page: any, size: any) {
    listRecommendProduct({page, size}).then((res: any) => {
      this.productMap = res.data
      this.$log.info('获取内容', res)
    })
  }
}
</script>

<style scoped lang="scss">
.banner-wrap {
  position: relative;
  img {
    width: 100%;
    overflow: hidden;
  }
}
.category {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 99;
  margin-left: 100px;
  .category-list {
    height: 450px;
    background: rgba(255, 255, 255, .6);
    padding: 0;
    li {
      list-style: none;
      padding: 3px 30px;
      cursor: pointer;
    }
    li:hover {
      background: rgb(255, 247, 247);
    }
  }
}
.content-menu {
  ul { padding: 0; }
  li {
    list-style: none;
    display: inline;
    padding: 0 5px;
    font-size: 15px;
    color: #666;
  }
  li:first-child {
    font-size: 20px;
  }
  li:not(:first-child):before {
    content: "|";
    margin-right: 7px;
    font-weight: 300;
  }
  .sideIcon {
    width: 8px !important;
    height: 20px !important;
    float: left;
    margin-top: 5px;
    margin-right: 10px;
  }
}

</style>