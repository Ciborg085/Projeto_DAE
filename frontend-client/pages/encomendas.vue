<template>
    <div>
        <OrderCreate v-if="authStore.role =='Client'"/>
        <OrderList />
    </div>
</template>

<script setup lang="ts">
import OrderCreate from '@/components/OrderCreate.vue';
import OrderList from '@/components/OrderList.vue';

import { useAuthStore } from '@/stores/auth';
import { onMounted } from 'vue';

const authStore = useAuthStore();

onMounted(() => {
    authStore.initAuth();
})


// Se quiseres forçar layout "default" ou outro:
definePageMeta({
  layout: 'default'
});
</script>

<style scoped>
</style>
