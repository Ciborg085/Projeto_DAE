<template>
  <OrderList />
</template>

<script setup lang="ts">
import OrderList from '@/components/OrderList.vue';

// Se quiseres forçar layout "default" ou outro:
definePageMeta({
  layout: 'default'
});
</script>

<style scoped>
</style>
