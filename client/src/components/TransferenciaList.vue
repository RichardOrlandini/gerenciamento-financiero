<template>
    <div>
      <h2>Extrato de Transferências</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Conta Origem</th>
            <th>Conta Destino</th>
            <th>Valor</th>
            <th>Taxa</th>
            <th>Data Agendamento</th>
            <th>Data Transferência</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="transferencia in transferencias" :key="transferencia.id">
            <td>{{ transferencia.id }}</td>
            <td>{{ transferencia.contaOrigem }}</td>
            <td>{{ transferencia.contaDestino }}</td>
            <td>{{ transferencia.valor }}</td>
            <td>{{ transferencia.taxa }}</td>
            <td>{{ transferencia.dataAgendamento }}</td>
            <td>{{ transferencia.dataTransferencia }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        transferencias: [],
      };
    },
    created() {
      this.carregarTransferencias();
    },
    methods: {
      carregarTransferencias() {
        axios
          .get('http://localhost:8081/api/transferencias')
          .then((response) => {
            this.transferencias = response.data;
          })
          .catch((error) => {
            alert('Erro ao carregar transferências: ' + error.message);
          });
      },
    },
  };
  </script>
  
  <style scoped>
  table {
    width: 100%;
    border-collapse: collapse;
  }
  thead {
    background-color: #f2f2f2;
  }
  th, td {
    padding: 10px;
    border: 1px solid #ddd;
    text-align: left;
  }
  tbody tr:hover {
    background-color: #f1f1f1;
  }
  </style>
  