<template>
  <header class="app-header">
    <div class="header-content">
      <!-- Logotipo e nome do sistema -->
      <div class="header-logo">
        <img src="/favicon.ico" alt="Logotipo" class="logo" />
        <h1>Monitorização XYZ</h1>
      </div>

      <!-- Menu de navegação -->
      <nav class="app-nav">
        <ul>
          <li>
            <NuxtLink to="/" exact-active-class="active-link">Início</NuxtLink>
          </li>
          <li>
            <NuxtLink to="/sensores" exact-active-class="active-link">Sensores</NuxtLink>
          </li>
          <li>
            <NuxtLink to="/encomendas" exact-active-class="active-link">Encomendas</NuxtLink>
          </li>
          <li>
            <NuxtLink to="/gestao" exact-active-class="active-link">Gestão</NuxtLink>
          </li>
        </ul>
      </nav>

      <!-- Login/Perfil -->
      <div class="user-actions">
        <template v-if="loggedIn">
          <!-- Menu de perfil -->
          <div class="profile-menu">
            <button class="profile-btn" @click="toggleDropdown">
              Perfil
            </button>
            <div class="dropdown" v-if="dropdownVisible">
              <ul>
                <li><NuxtLink to="/perfil">Meu Perfil</NuxtLink></li>
                <li><NuxtLink to="/configuracoes">Configurações</NuxtLink></li>
                <li><button @click="logout">Sair</button></li>
              </ul>
            </div>
          </div>
        </template>
        <template v-else>
          <!-- Botão de Iniciar Sessão -->
          <button class="login-btn" @click="login">Iniciar Sessão</button>
        </template>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      loggedIn: false, // Estado do login
      dropdownVisible: false, // Controle de visibilidade do dropdown
    };
  },
  methods: {
    login() {
      // Lógica de autenticação -> Trocar para "logado"
      this.loggedIn = true;
      this.dropdownVisible = false;
    },
    logout() {
      // Lógica para deslogar
      this.loggedIn = false;
    },
    toggleDropdown() {
      this.dropdownVisible = !this.dropdownVisible;
    },
  },
};
</script>

<style scoped>
/* Header fixo */
.app-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background-color: #1f1f26; /* Preto suave */
  color: #d4d4d8; /* Cinza pastel */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  z-index: 1000;
  padding: 0.5rem 1.5rem;
}

/* Conteúdo interno */
.header-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

/* Logotipo */
.header-logo {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo {
  width: 40px;
}

/* Navegação */
.app-nav ul {
  display: flex;
  list-style: none;
  margin: 0;
  padding: 0;
  gap: 15px;
}

.app-nav a {
  text-decoration: none;
  color: #d4d4d8;
  font-weight: 500;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.app-nav a:hover {
  background-color: #34343c;
  color: #ffffff;
}

.active-link {
  background-color: #d6d3f0; /* Lilás pastel */
  color: #16161a; /* Preto */
  font-weight: bold;
}

/* Ações do usuário (login ou perfil) */
.user-actions {
  display: flex;
  align-items: center;
  gap: 15px;
}

/* Botão de login */
.login-btn {
  background-color: #d6d3f0; /* Lilás pastel */
  color: #16161a;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.login-btn:hover {
  background-color: #b5b3e0; /* Variante de lilás */
}

/* Botão de perfil */
.profile-btn {
  background-color: #34343c; /* Preto suave */
  color: #d6d3f0; /* Lilás pastel */
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
  position: relative;
}

/* Dropdown do perfil */
.profile-menu {
  position: relative;
}

.dropdown {
  position: absolute;
  top: 100%;
  right: 0;
  background: #1f1f26; /* Fundo preto */
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  padding: 0.5rem 0;
  width: 150px;
}

.dropdown ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.dropdown li {
  padding: 0.5rem 1rem;
}

.dropdown li button,
.dropdown li a {
  color: #d4d4d8; /* Texto cinza pastel */
  text-decoration: none;
  background: none;
  border: none;
  cursor: pointer;
  width: 100%;
  text-align: left;
  padding: 0;
  font-size: 0.9rem;
}

.dropdown li button:hover,
.dropdown li a:hover {
  color: #ffffff;
}
</style>