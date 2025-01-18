<template>
  <div class="perfil-container">
    <h2>Bem-vindo, {{ authStore.username }}</h2>
    <p>Email: {{ authStore.email }}</p>
    <p>Role: {{ authStore.role }}</p>
    <button @click="authStore.logout">Sair</button>
  </div>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth';
import { onMounted } from 'vue';

const authStore = useAuthStore();

//definePageMeta({
//  middleware: 'auth'
//});

onMounted(() => {
    authStore.initAuth();
    authStore.fetchProfile();
});

</script>

<style scoped>
.perfil-container {
  text-align: center;
  padding: 2rem;
}

button {
  background-color: #e53935;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
}

button:hover {
  background-color: #d32f2f;
}
</style>
