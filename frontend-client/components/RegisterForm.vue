<template>
  <div class="register-form">
    <h2>Registar Conta</h2>

    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

    <form @submit.prevent="register">
      <input v-model="username" type="text" placeholder="Nome de Utilizador" required />
      <input v-model="name" type="text" placeholder="Nome Completo" required />
      <input v-model="email" type="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Senha" required />
      <button type="submit" :disabled="loading">
        {{ loading ? 'Criando Conta...' : 'Registrar' }}
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';

const emit = defineEmits(['register-success']);  // Emite evento para a página
const username = ref('');
const name = ref('');
const email = ref('');
const password = ref('');
const loading = ref(false);
const errorMessage = ref('');

const register = async () => {
  loading.value = true;
  errorMessage.value = '';

  try {
    const response = await fetch('http://localhost:8080/academics/api/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: username.value,
        name: name.value,  // Incluindo o campo obrigatório 'name'
        email: email.value,
        password: password.value,
      }),
    });

    if (response.ok) {
      emit('register-success');  // Informa sucesso à página
    } else {
      const result = await response.json();
      errorMessage.value = result.message || 'Erro ao criar conta.';
    }
  } catch (error) {
    errorMessage.value = 'Erro ao conectar com o servidor.';
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.register-form {
  max-width: 400px;
  margin: 50px auto;
  background-color: #fff;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

input, button {
  width: 100%;
  padding: 0.8rem;
  margin-bottom: 1rem;
  border-radius: 8px;
  border: 1px solid #ccc;
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
</style>
