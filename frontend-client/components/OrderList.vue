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
      <!-- Bloco para cada encomenda -->
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

        <!-- Quando 'volumesOpen[order.id]' é true, mostramos cada volume -->
        <tr
            v-if="volumesOpen[order.id]"
            v-for="volume in (volumesMap[order.id] || [])"
            :key="volume.id"
            class="volume-row"
        >
          <td colspan="5">
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Volume id</th>
                            <th>Estado</th>
                            <th>Quantidade</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>{{ volume.id }}</td>
                            <td>{{ volume.volume_status }}</td>
                            <td>{{ volume.quantity }}</td>
                            <td>
                                <button v-if="authStore.role=='Administrator'" @click="advanceVolume(volume)">Avançar o estado</button>
                                <button @click="abortVolume(volume)">Cancelar</button>
                            </td>
                        </tr>
                    </tbody>
                </table>

              <!-- Mostra dados do produto do volume (se existir) -->
              <div v-if="volume.product">
                <h3>Produto do Volume</h3>

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Marca</th>
                            <th>Categoria</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>{{ volume.product.id }}</td>
                            <td>{{ volume.product.name }}</td>
                            <td>{{ volume.product.brand }}</td>
                            <td>{{ volume.product.category }}</td>
                        </tr>
                    </tbody>
                </table>
                <!-- etc... -->
              </div>

              <!-- Mostra sensores do volume (se existirem) -->
              <div>
                <h4>Sensores</h4>
                <div v-if="!volume.sensor || !volume.sensor.length">
                  (Sem sensores ou não carregados)
                </div>
                <ul v-else>
                  <li
                      v-for="sensor in volume.sensor"
                      :key="sensor.id"
                  >
                  <strong>ID:</strong> {{ sensor.id }} |
                  <strong>Tipo:</strong> {{ sensor.type }}

                  <!-- Exibe propriedades de acordo com sensor.type -->
                  <template v-if="sensor.type === 'temperatureSensor'">
                      <br />
                      <strong>Temperatura:</strong> {{ sensor.properties.temperature }} °C
                  </template>

                  <template v-else-if="sensor.type === 'pressureSensor'">
                      <br />
                      <strong>Pressão:</strong> {{ sensor.properties.pressure }} hPa
                  </template>

                  <template v-else-if="sensor.type === 'multiSensor'">
                      <br />
                      <strong>Temperatura:</strong> {{ sensor.properties.temperature }} °C
                      <br />
                      <strong>Pressão:</strong> {{ sensor.properties.pressure }} hPa
                      <br />
                      <strong>Latitude:</strong> {{ sensor.properties.latitude }}
                      <br />
                      <strong>Longitude:</strong> {{ sensor.properties.longitude }}
                  </template>

                  <template v-else-if="sensor.type === 'geolocationSensor'">
                      <br />
                      <strong>Latitude:</strong> {{ sensor.properties.latitude }}
                      <br />
                      <strong>Longitude:</strong> {{ sensor.properties.longitude }}
                  </template>

                  <!-- Se houver outro tipo não previsto -->
                  <template v-else>
                      <br />
                      <em>Tipo de sensor não reconhecido.</em>
                  </template>
                  <!-- podes exibir outras props do sensor... -->
                  </li>
                </ul>
              </div>
            </div>
          </td>
        </tr>

        <!-- Se 'productsOpen[order.id]' for true, mostra os produtos GERAIS da encomenda -->
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

// Controla se a secção de produtos da encomenda está aberta
const productsOpen = ref<Record<number, boolean>>({});
// Armazena produtos de cada encomenda
const productsMap = ref<Record<number, any[]>>({});

const config = useRuntimeConfig();
const authStore = useAuthStore();
const BASE_URL = `${config.public.API_URL}/loja/encomendas`;

onMounted(async () => {
  authStore.initAuth();
  await fetchOrders();
});

/**
 * Busca a lista de encomendas
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
    orders.value = data;
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
  if (volumesOpen.value[order.id]) {
    // Já está aberto -> fecha
    volumesOpen.value[order.id] = false;
    return;
  }

  // Se ainda não carregámos volumes desta encomenda, buscamos
  if (!volumesMap.value[order.id]) {
    try {
      const response = await fetch(`${BASE_URL}/${order.id}/volumes`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${authStore.token}`,
        },
      });
      if (!response.ok && response.status !== 204) {
        throw new Error('Falha ao buscar volumes');
      }
      if (response.status === 204) {
        volumesMap.value[order.id] = [];
      } else {
        const data = await response.json();
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
 * Expandir/colapsar produtos GERAIS da encomenda
 */
async function toggleProducts(order: any) {
  if (productsOpen.value[order.id]) {
    // Já está aberto -> fecha
    productsOpen.value[order.id] = false;
    return;
  }

  // Se ainda não carregámos os produtos
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
      productsMap.value[order.id] = data.products || [];
    } catch (err) {
      console.error(err);
      productsMap.value[order.id] = [];
    }
  }

  productsOpen.value[order.id] = true;
}

const advanceVolume = async (volume: any) => {
    let updateData = {
        "status": ""
    };
    
    switch(volume.volume_status) {
        case 'SENT':
            updateData.status = 'delivered';
            break;
        case 'ABORTED':
            alert('Volume already aborted');
            return;
        case 'DELIVERED':
            alert('Volume already delivered');
            return;
        default:
            alert(`Volume #${volume.id} with invalid status`);
            return;
    }

    try {
        const response = await fetch(`${config.public.API_URL}/loja/volumes/${volume.id}`,
        {
            method: 'PATCH',
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${authStore.token}`,
            },
            body: JSON.stringify(updateData),
        });
        
        if (response.status != 200) {
            alert(response.body);
        }
        refreshVolumes(volume.order_id);


    } catch (err) {
        console.log(err);
        alert(err);
    }
}

const abortVolume = async (volume: any) => {
    let updateData = {
        "status": "aborted"
    };

    if(volume.volume_status == 'DELIVERED') {
        alert("Already delived");
        return;
    }

    if(volume.volume_status == 'ABORTED') {
        alert("Already aborted");
        return;
    }

    try {
        const response = await fetch(`${config.public.API_URL}/loja/volumes/${volume.id}`,
        {
            method: 'PATCH',
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${authStore.token}`,
            },
            body: JSON.stringify(updateData),
        });
        
        if (response.status != 200) {
            alert(response.body);
        }
        refreshVolumes(volume.order_id);


    } catch (err) {
        console.log(err);
        alert(err);
    }
}
const refreshVolumes = async (orderId: number) => {
  try {
    const response = await fetch(`${BASE_URL}/${orderId}/volumes`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${authStore.token}`,
      },
    });

    if (!response.ok && response.status !== 204) {
      throw new Error('Failed to refresh volumes');
    }

    if (response.status === 204) {
      volumesMap.value[orderId] = [];
    } else {
      const data = await response.json();
      volumesMap.value[orderId] = data.volumes || [];
    }
  } catch (err) {
    console.error('Error refreshing volumes:', err);
    alert('Error refreshing volume data');
  }
};

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

.volume-row div {
  margin-bottom: 0.5rem;
}
</style>
