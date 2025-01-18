<template>
  <div class="order-list">
    <h2>Lista de Encomendas</h2>

    <!-- Mensagens de estado -->
    <div v-if="loading">A carregar encomendas...</div>
    <div v-else-if="error">Ocorreu um erro ao carregar as encomendas.</div>

    <table v-else>
      <thead>
      <tr>
        <th>ID</th>
        <th>Cliente</th>
        <th>Estado</th>
        <th>Destino</th>
        <th>Ações</th>
      </tr>
      </thead>
      <tbody>
      <!-- Aqui usamos <template> v-for em vez de <tr> v-for -->
      <template v-for="order in orders" :key="order.id">
        <!-- Linha principal da encomenda -->
        <tr class="order-row">
          <td>{{ order.id }}</td>
          <td>{{ order.clientUsername }}</td>
          <td>{{ order.orderStatus }}</td>
          <td>{{ order.destination }}</td>
          <td>
            <button @click="toggleVolumes(order)">Ver Volumes</button>
            <button @click="toggleProducts(order)">Ver Produtos</button>
          </td>
        </tr>

        <!-- Linha(s) extra se volumesOpen[order.id] estiver true -->
        <tr
            v-if="volumesOpen[order.id]"
            v-for="volume in (volumesMap[order.id] || [])"
            :key="volume.id"
            class="volume-row"
        >
          <td colspan="5">
            <strong>Volume #{{ volume.id }}</strong>
            <div>Estado: {{ volume.volume_status }}</div>
            <div>Quantidade: {{ volume.quantity }}</div>
            <!-- etc. -->
          </td>
        </tr>

        <!-- Linha(s) extra se productsOpen[order.id] estiver true -->
        <tr
            v-if="productsOpen[order.id]"
            class="products-row"
        >
          <td colspan="5">
            <h4>Produtos da Encomenda {{ order.id }}</h4>
            <div v-if="!productsMap[order.id]?.length">
              (Sem produtos / ou a carregar...)
            </div>
            <ul v-else>
              <li
                  v-for="prod in productsMap[order.id]"
                  :key="prod.id"
              >
                {{ prod.name }} - {{ prod.brand }} ({{ prod.category }})
              </li>
            </ul>
          </td>
        </tr>
      </template>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRuntimeConfig } from '#app';
import { useAuthStore } from '@/stores/auth';

// Lista de encomendas
const orders = ref<any[]>([]);
const loading = ref(true);
const error = ref(false);

// Controla se a secção de volumes está aberta para cada encomenda
const volumesOpen = ref<Record<number, boolean>>({});
// Armazena volumes de cada encomenda
const volumesMap = ref<Record<number, any[]>>({});

// Controla se a secção de produtos está aberta para cada encomenda
const productsOpen = ref<Record<number, boolean>>({});
// Armazena produtos de cada encomenda
const productsMap = ref<Record<number, any[]>>({});

const config = useRuntimeConfig();
const authStore = useAuthStore();

// URL base (podes ajustar conforme precisas)
const BASE_URL = `${config.public.API_URL}/loja/encomendas`;

onMounted(async () => {
  authStore.initAuth();
  await fetchOrders();
});

/**
 * Busca a lista de encomendas do cliente
 */
async function fetchOrders() {
  loading.value = true;
  error.value = false;
  try {
    const response = await fetch(`${BASE_URL}/`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${authStore.token}`,
      },
    });
    if (!response.ok) {
      throw new Error('Falha ao buscar encomendas');
    }
    const data = await response.json();
    orders.value = data; // array de encomendas
  } catch (err) {
    console.error(err);
    error.value = true;
  } finally {
    loading.value = false;
  }
}

/**
 * Expandir/colapsar volumes de uma encomenda
 */
async function toggleVolumes(order: any) {
  // Se já está aberto, fecha
  if (volumesOpen.value[order.id]) {
    volumesOpen.value[order.id] = false;
    return;
  }

  // Caso contrário, buscamos volumes só se ainda não os temos em cache
  if (!volumesMap.value[order.id]) {
    try {
      const response = await fetch(`${BASE_URL}/${order.id}/volumes`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${authStore.token}`,
        },
      });
      // Se não houver volumes ou der erro, lida com isso
      if (!response.ok && response.status !== 204) {
        throw new Error('Falha ao buscar volumes');
      }
      // Se vier 204 (NO_CONTENT), volumes = []
      if (response.status === 204) {
        volumesMap.value[order.id] = [];
      } else {
        const data = await response.json();
        // A resposta do back-end é um objeto com { volumes: [...] }
        volumesMap.value[order.id] = data.volumes ?? [];
      }
    } catch (err) {
      console.error(err);
      volumesMap.value[order.id] = [];
    }
  }
  volumesOpen.value[order.id] = true;
}

/**
 * Expandir/colapsar produtos de uma encomenda
 */
async function toggleProducts(order: any) {
  // Se já estiver aberto, fecha
  if (productsOpen.value[order.id]) {
    productsOpen.value[order.id] = false;
    return;
  }

  // Só busca se ainda não foi carregado
  if (!productsMap.value[order.id]) {
    try {
      const response = await fetch(`${BASE_URL}/${order.id}`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${authStore.token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Falha ao buscar produtos da encomenda');
      }
      const data = await response.json();
      // data deve vir no formato:
      // {
      //   id: ...,
      //   clientUsername: ...,
      //   orderStatus: ...,
      //   products: [ { id, name, ... } ]
      // }
      productsMap.value[order.id] = data.products || [];
    } catch (err) {
      console.error(err);
      productsMap.value[order.id] = [];
    }
  }

  productsOpen.value[order.id] = true;
}
</script>

<style scoped>
.order-list {
  padding: 2rem;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  max-width: 900px;
  margin: 0 auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

thead th {
  background-color: #f5f5f7;
  padding: 0.5rem;
  text-align: left;
}

tbody td {
  padding: 0.5rem;
  border-top: 1px solid #ddd;
}

tbody tr:hover {
  background-color: #f1f1f1;
}

.volume-row,
.products-row {
  background-color: #fafafa;
  border-top: 1px solid #eee;
}
</style>
