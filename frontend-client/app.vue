<template>
  <div class="app-container">
    <!-- Componente de Cabeçalho -->
    <Header />

    <!-- Conteúdo principal renderizado dinamicamente -->
    <main class="app-content">
      <NuxtPage />
    </main>

    <!-- Rodapé global -->
    <footer class="app-footer">
      <span>&copy; 2024 Monitorização XYZ. ESTG-IPL</span>
    </footer>
  </div>
</template>

<script setup>
// Importando o componente de cabeçalho
import Header from '~/components/Header.vue';
</script>

<style scoped>
/* Contêiner principal */
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  font-family: "Inter", sans-serif;
  background: #f5f5f7; /* Fundo cinza claro */
  color: #333; /* Texto em cinza escuro */
  padding-top: 70px; /* Compensar altura do header fixo */
}

.app-content {
  flex: 1;
  padding: 2rem;
  max-width: 900px;
  margin: 0 auto;
  background-color: #ffffff; /* Branco */
  border-radius: 12px; /* Bordas arredondadas */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05); /* Sombra suave */
}

/* Rodapé */
.app-footer {
  background-color: #1f1f26;
  color: #d4d4d8; /* Cinza pastel */
  text-align: center;
  padding: 1rem;
  font-size: 0.9rem;
  border-top: 2px solid #e4e4e7;
}
</style>
