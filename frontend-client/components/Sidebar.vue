<template>
  <aside class="sidebar">
    <NuxtLink to="/produtos">
      📦 Produtos
    </NuxtLink>
    <NuxtLink to="/sensores">
      🔍 Sensores
    </NuxtLink>
    <NuxtLink to="/encomendas">
      🚚 Encomendas
    </NuxtLink>

  </aside>
</template>

<style scoped>
/* Sidebar fixa */
.sidebar {
  width: 250px;
  background-color: #1f1f26;
  color: #d4d4d8;
  height: calc(100vh - 70px);
  padding-top: 1rem;
  position: fixed;
  top: 70px; /* Alinhado com o Header */
  left: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
}

/* Links do menu */
.sidebar a {
  color: #d4d4d8;
  text-decoration: none;
  padding: 1rem;
  display: flex;
  align-items: center;
  transition: background-color 0.3s ease;
}

.sidebar a:hover,
.sidebar a.active {
  background-color: #34343c;
  color: #ffffff;
}
</style>
