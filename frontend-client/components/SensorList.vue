<template>
  <div class="sensor-list">
    <h2>Lista de Sensores </h2>
    <!-- Exibição de erro -->
    <div v-if="error" class="error">{{ error }}</div>

    <!-- Loader enquanto busca os dados -->
    <div v-if="loading" class="loading">Carregando sensores...</div>

    <!-- Lista de sensores -->
    <ul v-else>
      <li v-for="sensor in sensors" :key="sensor.id" class="sensor-item">
        <strong>ID:</strong> {{ sensor.id }} |
        <strong>Tipo:</strong> {{ sensor.type }} |
        <strong>Criação:</strong> {{ formatDate(sensor.created_at) }}
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      sensors: [], // Lista de sensores
      loading: true, // Estado de carregamento
      error: null, // Estado de erro
    };
  },
  methods: {
    async fetchSensors() {
      try {
        this.loading = true;
        this.error = null;

        // Realiza a chamada para a API de sensores
        const response = await axios.get("/api/sensors", {
          headers: {
            "end-user-token": "SEU_TOKEN_AQUI", // Substitua pelo token correto
          },
          params: {
            type: "acceleration sensor", // Pode ajustar se for necessário filtrar
          },
        });

        console.log("Resposta da API:", response); // Exibe a resposta completa da API no console
        console.log("Data da API:", response.data); // Exibe os dados da propriedade `data`

        // Configura os sensores para exibir apenas os 10 primeiros
        this.sensors = response.data.sensors.slice(0, 10);
      } catch (err) {
        // Caso ocorra erro na solicitação
        this.error = "Erro ao carregar os sensores! Verifique as permissões.";
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    formatDate(date) {
      // Formata a data para algo mais legível
      return new Date(date).toLocaleString();
    },
  },
  mounted() {
    // Busca os sensores ao montar o componente
    this.fetchSensors();
  },
};
</script>

<style scoped>
.sensor-list {
  padding: 1rem;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 1rem;
}

.sensor-item {
  margin-bottom: 0.5rem;
  padding: 0.5rem;
  border-bottom: 1px solid #e5e5e5;
}

.error {
  color: red;
  font-weight: bold;
}

.loading {
  color: #555;
}
</style>