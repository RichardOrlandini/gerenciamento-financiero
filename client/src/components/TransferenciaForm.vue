<template>
    <div>
      <h2>Agendar Transferência</h2>
      <form @submit.prevent="agendarTransferencia">
        <div>
          <label for="contaOrigem">Conta Origem:</label>
          <input v-model="transferencia.contaOrigem" placeholder="XXXXXXXXXX" required />
        </div>
        <div>
          <label for="contaDestino">Conta Destino:</label>
          <input v-model="transferencia.contaDestino" placeholder="XXXXXXXXXX" required />
        </div>
        <div>
          <label for="valor">Valor:</label>
          <input v-model.number="transferencia.valor" type="number" placeholder="Valor" required />
        </div>
        <div>
          <label for="dataTransferencia">Data de Transferência:</label>
          <input v-model="transferencia.dataTransferencia" type="date" required />
        </div>
        <button type="submit">Agendar</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        transferencia: {
          contaOrigem: '',
          contaDestino: '',
          valor: null,
          dataTransferencia: '',
        },
      };
    },
    methods: {
      agendarTransferencia() {
        const hoje = new Date().toISOString().split('T')[0]; // Data de hoje no formato YYYY-MM-DD
        const payload = {
          ...this.transferencia,
          dataAgendamento: hoje,
        };
        axios
          .post('http://localhost:8081/api/transferencias', payload)
          .then(() => {
            alert('Transferência agendada com sucesso!');
            this.transferencia = {
              contaOrigem: '',
              contaDestino: '',
              valor: null,
              dataTransferencia: '',
            };
          })
          .catch((error) => {
            alert('Erro ao agendar transferência: ' + error.response.data.message);
          });
      },
    },
  };
  </script>
  
  <style scoped>
  form div {
    margin-bottom: 15px;
  }
  label {
    display: block;
    font-weight: bold;
  }
  input {
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    box-sizing: border-box;
  }
  button {
    padding: 10px 15px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
  }
  button:hover {
    background-color: #0056b3;
  }
  </style>
  