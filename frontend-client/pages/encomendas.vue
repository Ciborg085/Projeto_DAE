<template>
    <div>
        <VolumeCreate v-if="authStore.role =='Administrator'" />
        <OrderCreate v-if="authStore.role =='Client'"/>
        <OrderList />
    </div>
</template>

<script setup lang="ts">
import VolumeCreate from '@/components/VolumeCreate.vue';
import OrderCreate from '@/components/OrderCreate.vue';
import OrderList from '@/components/OrderList.vue';

import { onMounted } from 'vue';
import { useAuthStore } from '@/stores/auth';

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
