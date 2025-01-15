<template>
  <div class="login-form">
    <h2>Iniciar Sessão</h2>
    <form @submit.prevent="login">
      <input v-model="username" type="text" placeholder="Nome de Utilizador" required />
      <input v-model="password" type="password" placeholder="Senha" required />
      <button type="submit">Entrar</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const username = ref('');
const password = ref('');
const router = useRouter();

const login = async () => {
  try {
    const response = await fetch('http://localhost:8080/academics/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username: username.value, password: password.value }),
    });

    if (response.ok) {
      alert('Login bem-sucedido!');
      router.push('/perfil');
    } else {
      alert('Credenciais inválidas!');
    }
  } catch (error) {
    console.error('Erro ao autenticar:', error);
  }
};
</script>

<style scoped>
.login-form {
  max-width: 400px;
  margin: 50px auto;
}
</style>
