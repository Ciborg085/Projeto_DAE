<template>
  <div class="app-container">
    <!-- Cabeçalho -->
    <Header />

    <div class="main-wrapper">
      <!-- Sidebar fixa -->
      <Sidebar />

      <!-- Área principal de conteúdo -->
      <main class="main-content">
        <NuxtPage />
      </main>
    </div>
  </div>
</template>

<script setup>
import Header from '~/components/Header.vue';
import Sidebar from '~/components/Sidebar.vue';
</script>

<style scoped>
/* Container principal */
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

/* Wrapper para Sidebar + Conteúdo */
.main-wrapper {
  display: flex;
  flex: 1;
  margin-top: 70px; /* Espaço para o Header fixo */
}

/* Conteúdo principal */
.main-content {
  flex: 1;
  padding: 2rem;
  background-color: #f5f5f7;
  min-height: calc(100vh - 70px);
  overflow-y: auto;
}
</style>
