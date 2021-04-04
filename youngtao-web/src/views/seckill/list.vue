<template>
  <div class="container product-wrap">
    <ul v-if="menus.length != 0" class="hoursMenu shadow-sm clearfix">
      <li v-for="(n, i)  in 5" :key="i" @click="changeMenu(i)"
        :class="{active: i==0}">
        <div class="clearfix">
          <el-col :span="10" class="menu-time">
            {{menus[i].substring(8)}} : 00
          </el-col>
          <el-col :span="14" v-if="active == i" class="menu-show">
            <div v-if="i == 0">
              <div>正在秒杀</div>
              <div>距离结束：xxx</div>
            </div>
            <div v-else>
              <div>即将开始</div>
              <div>距离开始：xxx</div>
            </div>
          </el-col>
          <el-col :span="14" v-else >
            <div v-if="i == 0" >
              <span class="menu-hint">进行中</span>
            </div>
            <div v-else>
              <span class="menu-hint">即将开始</span>
            </div>
          </el-col>
        </div>
      </li>
    </ul>
    <el-row :gutter="20">
      <el-col v-for="product in productList" :key="product.spuId" :md="6">
        <!-- 首页显示第一个sku -->
        <product-card :product="product" :time="menus[active]" />
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import ProductCard from './components/ProductCard.vue'
import { getTimeMenu, getProductList } from '@/api/gsc/product'

@Component({
  components: {
    ProductCard
  }
})
export default class SeckillList extends Vue {
  private active = 0
  private menus: any = []
  private productList: any = []
  
  // 切换菜单
  private changeMenu(index: number) {
    this.active = index
    this.getProduct(index, 1, 10);
    $('.hoursMenu li').each((i: any, e: any) => {
      if (i !== index) {
        e.classList.remove('active')
      } else {
        e.classList.add('active')
      }
    })
  }
  
  // 获取秒杀菜单
  private getTimeMenu() {
    getTimeMenu().then((res: any) => {
      this.menus = res.data
      this.getProduct(0, 1, 10)
      this.$log.info('获取菜单', res.data)
    })
  }
  
  // 获取菜单对应的秒杀商品
  private getProduct(idx: any, page: any, size: any) {
    getProductList(idx, page, size).then((res: any) => {
      this.productList = res.data
      this.$log.info('秒杀商品', res.data)
    })
  }
  
  private mounted() {
    this.getTimeMenu()
  }
}
</script>

<style scoped lang="scss">
.hoursMenu {
  padding: 0;
  background-color: #fff;
  li {
    float: left;
    list-style: none;
    width: 20%;
    height: 60px;
    cursor: pointer;
  }
  li:hover {
    background: rgba(255, 205, 205, .3);   
  }
  .active {
    background: rgb(255, 205, 205) !important;   
  }
  .menu-time {
    padding: 15px;
  }
  .menu-show {
    padding-top: 5px;
  }
  .menu-hint {
    display: inline-block;
    border: 1px solid #666;
    border-radius: 10px;
    padding: 1px 10px;
    margin-top: 15px;
  }
}
</style>
