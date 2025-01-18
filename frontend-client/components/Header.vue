<template>
    <header class="app-header">
        <div class="header-content">
            <div class="header-logo">
                <img src="/favicon.ico" alt="Logotipo" class="logo" />
                <h1>Monitorização XYZ</h1>
            </div>

            <nav class="app-nav">
                <NuxtLink to="/">Início</NuxtLink>
                <NuxtLink to="/produtos">Produtos</NuxtLink>
                <NuxtLink to="/sensores">Sensores</NuxtLink>
                <NuxtLink to="/encomendas">Encomendas</NuxtLink>

            </nav>

            <div v-if="!authStore.isAuthenticated()" class="user-actions">
                <NuxtLink to="/login" class="login-btn">Iniciar Sessão</NuxtLink>
            </div>
            <div v-else class="user-actions">
                <NuxtLink to="/perfil" class="login-btn">Perfil</NuxtLink>
            </div>
        </div>
    </header>
</template>

<script setup>
import { onMounted } from 'vue';
import { useAuthStore } from '@/stores/auth';
const authStore = useAuthStore();
    
onMounted(() => {
    authStore.initAuth();
})

</script>

<style scoped>
/* Header fixo */
.app-header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    height: 70px;
    background-color: #1f1f26;
    color: #d4d4d8;
    display: flex;
    align-items: center;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    z-index: 1000;
}

/* Conteúdo interno */
.header-content {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    padding: 0 1.5rem;
}

/* Logo */
.header-logo {
    display: flex;
    align-items: center;
    gap: 10px;
}

.logo {
    width: 40px;
}

/* Navegação */
.app-nav a {
    color: #d4d4d8;
    text-decoration: none;
    margin: 0 10px;
    font-weight: 500;
}

.app-nav a:hover {
    color: #ffffff;
}

/* Botão de Login */
.login-btn {
    background-color: #4caf50;
    color: #ffffff;
    padding: 0.5rem 1rem;
    border-radius: 8px;
    text-decoration: none;
    font-weight: bold;
    transition: background-color 0.3s ease;
}

.login-btn:hover {
    background-color: #388e3c;
}
</style>
