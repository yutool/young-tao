<template>
  <div>
    <div class="text-center pt-3 pb-3">
      <div @click="$router.push('/user')" class="pointer">
        <el-avatar class="user-avatar" :size="36">
          <img v-if="user" :src="user.avatar"/>
          <span v-else>游客</span>
        </el-avatar>
        <div>{{ user.username }}</div>
      </div>
    </div>
    <div class="menu-wrap">
      <div>
        <div class="menu-title">
          <i class="el-icon-goods"></i> 订单中心
        </div>
        <div class="menu-item" :class="{'active': activePath == '/center/order'}" @click="handlerClick('/center/order')">我的订单</div>
        <div class="menu-item">团购订单</div>
        <div class="menu-item">退款退货</div>
        <div class="menu-item" :class="{'active': activePath == '/center/orderRecycle'}" @click="handlerClick('/center/orderRecycle')">订单回收站</div>
      </div>
      <div>
        <div class="menu-title">
          <i class="el-icon-star-off"></i> 关注中心
        </div>
        <div class="menu-item">关注的店铺</div>
        <div class="menu-item">关注的商品</div>
        <div class="menu-item">关注的品牌</div>
        <div class="menu-item">浏览记录</div>
      </div>
      <div>
        <div class="menu-title">
          <i class="el-icon-user"></i> 客户服务
        </div>
        <div class="menu-item">返修退换货</div>
        <div class="menu-item">交易纠纷</div>
        <div class="menu-item">我的发票</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter, State } from 'vuex-class'

@Component
export default class SideBar extends Vue {
  @State(((state: any) => state.account.user)) private user: any

  private activePath = '/center/order'

  private handlerClick(path: string) {
    this.activePath = path
    this.$router.push(path)
  }

  private mounted() {
    this.activePath = this.$route.path
  }
}
</script>

<style scoped lang="scss">
.menu-wrap {
  .menu-title {
    padding: 10px;
    margin-left: 30px;
  }
  .menu-item {
    padding: 6px;
    padding-left: 70px;
    cursor: pointer;
  }
  .active {
    background-color: #ebf2fe;
    color: #3582fb;
    border-left: 5px solid #3582fb;
    padding-left: 65px;
  }
}
</style>