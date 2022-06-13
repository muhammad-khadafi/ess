<template>
  <section class="hero">
    <div class="hero-body">
      <div class="container-header">
        <div class="columns is-vcentered is-mobile">
          <div class="column is-2">
            <a class="navbar-item level-left" href="#">
              <img class="logo-header" src="../../assets/ESS-logo-2.png">
            </a>
          </div>
          <div class="column header">
            <h1 class="title">
              Employee Self Service System
            </h1>
            <h2 class="subtitle">
              Next Generation
            </h2>
          </div>
          <div class="column logout">
            <p v-if="auth.isAuthenticated">
            Welcome <b>{{auth.getUserInfo().username}}</b>, You are login as <b>{{auth.getUserInfo().label}}</b> | <a v-if="auth.isAuthenticated" @click="logout()">Logout<i class="icon-logout fa fa-sign-out fa-lg"></i></a>
            </p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
  import auth from 'src/shared/auth'
  // import axios from 'axios'

  export default {
    components: {
    },
    data () {
      return {
        auth: auth
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      logout () {
        console.log('LOGOUT')
        this.auth.logout()
        console.log('ROUTER nya si sidebar : ', this.$router)
        this.$router.replace('/login')
      },
      fetchData () {
      }
    }
  }
</script>
<style>
  .hero-body{
    background-color: #FFFF;
  }
  
  .container-header{
    margin-left: 5%;
  }

  .column.header{
    margin-left: -4%;
  }

  .column.header h1{
    font-size: 35px;
  }

  .column.header h2{
    font-size: 20px;
  }

  .column.logout{
    color: #363636;
    margin-right: 5%;
  }

  .column.logout a{
    color: #83BFD1;
    font-size: 0.9rem;
    transition: .3s;
  }

  .column.logout a:hover{
    color: #FFC107;
  }

  .column.logout p{
    text-align: right;
  }

  .icon-logout{
    padding-left: 5px;
    color: #83BFD1;
  }

  .icon-logout:hover{
    color: #FFC107;
  }

  .logo-header{
    width: 40%;
    margin-left: 100px;
  }

  @media screen and (max-width: 1800px){
    .column.header{
      margin-left: -50px;
    }
    .logo-header{
    width: 55%;
    margin-left: 60px;
    }
  }
  @media screen and (max-width: 1500px){
    .column.header{
      margin-left: -15px;
    }
    .logo-header{
    width: 65%;
    margin-left: 60px;
    }
  }
  @media screen and (max-width: 1300px){
    .column.header{
      margin-left: 5px;
    }
    .logo-header{
    width: 80%;
    margin-left: 60px;
    }
  }

  @media screen and (max-width: 1100px){
    .logo-header{
      display: none;
    }
    .column.header{
      margin-left: -150px;
    }
  }
  @media screen and (max-width: 1000px){
    .column.header{
      display: none;
    }
    .logo-header{
      display: none;
    }
    .column.logout p{
      text-align: left;
    }
  }

  @media screen and (max-width: 650px){
    .column.logout{
      margin-left: -30px;
    }
  }
  
</style>
