<template>
  <div class="login-container">
    <div class="login-card">
      <h2>Iniciar Sessão</h2>

      <p v-if="authStore.error" class="error-message">{{ authStore.error }}</p>

      <form @submit.prevent="handleLogin">
        <input v-model="username" type="text" placeholder="Nome de Utilizador" required />
        <input v-model="password" type="password" placeholder="Senha" required />
        <button type="submit" :disabled="authStore.loading">
          {{ authStore.loading ? 'Entrando...' : 'Entrar' }}
        </button>
      </form>

      <NuxtLink to="/register" class="register-link">Criar Conta</NuxtLink>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

const username = ref('');
const password = ref('');
const router = useRouter();
const authStore = useAuthStore();

const handleLogin = async () => {
  await authStore.login(username.value, password.value);

  if (authStore.token) {
    router.push('/perfil');
  }
};
</script>


<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
}

.login-card {
  background-color: #fff;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  text-align: center;
}

input, button {
  width: 100%;
  padding: 0.8rem;
  margin-bottom: 1rem;
  border-radius: 8px;
}

button {
  background-color: #4caf50;
  color: #fff;
  border: none;
  cursor: pointer;
}

button:disabled {
  background-color: #a5d6a7;
  cursor: not-allowed;
}

.error-message {
  color: red;
  margin-bottom: 1rem;
}

.register-link {
  display: block;
  margin-top: 1rem;
  color: #4caf50;
  text-decoration: none;
  font-weight: bold;
}

.register-link:hover {
  text-decoration: underline;
}
</style>
