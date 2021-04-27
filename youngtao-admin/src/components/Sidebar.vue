<template>
  <el-menu :default-active="activePath" class="el-menu-vertical-demo" router :default-openeds="['1', '2']">
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-s-goods"></i>
        <span>商品管理</span>
      </template>
      <el-menu-item-group title="常规商品">
        <el-menu-item index="/product/list" route="/product/list">
          <i class="el-icon-document"></i>商品列表
        </el-menu-item>
        <el-menu-item index="/product/add" route="/product/add">
          <i class="el-icon-sell"></i>添加商品
        </el-menu-item>
        <el-menu-item index="/product/recycle" route="/product/recycle">
          <i class="el-icon-sell"></i>回收站
        </el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="活动商品">
        <el-menu-item index="/seckill/list" route="/seckill/list">
          <i class="el-icon-document"></i>活动列表
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-s-order"></i>
        <span>订单管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/order/list" route="/order/list">
          <i class="el-icon-document-copy"></i>全部订单
        </el-menu-item>
        <el-menu-item index="/order/pending" route="/order/pending">
          <i class="el-icon-document-checked"></i>待发货订单
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Navbar extends Vue {
  private activePath = '/product/list'

  private created() {
    this.activePath = this.$route.path
  }
}
</script>

<style scoped lang="scss">
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 300px;
  min-height: 100vh;
  background-color: #fff;
}
</style>
