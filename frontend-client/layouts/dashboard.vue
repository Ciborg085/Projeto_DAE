<template>
  <div class="dashboard-container">
    <Header />
    <aside class="sidebar">
      <NuxtLink to="/">Início</NuxtLink>
      <NuxtLink to="/produtos">Produtos</NuxtLink>
      <NuxtLink to="/sensores">Sensores</NuxtLink>
      <NuxtLink to="/encomendas">Encomendas</NuxtLink>
    </aside>

    <main class="dashboard-content">
      <NuxtPage />
    </main>
  </div>
</template>

<script setup>
import Header from '~/components/Header.vue';
</script>

<style scoped>
.dashboard-container {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 250px;
  background-color: #1f1f26;
  color: white;
  padding: 1rem;
}

.sidebar a {
  display: block;
  margin-bottom: 10px;
  color: white;
  text-decoration: none;
}

.dashboard-content {
  flex: 1;
  padding: 2rem;
  background-color: #f5f5f7;
}
</style>
