<template>
<div class= "line-transparant">
  <div id="line"> </div>
<div>
  <div class="container login">
    <div class="background-login">
			<img src="../assets/bg.svg">
	</div>

    <div class="login-container">
      <form>
        <img class="avatar" src="../assets/ESS-logo-2.png">
        <h2> Employee Self Service System</h2>
        <h3> Next Generation </h3>

          <div class="input-div one focus">

            <div class="i">
						  <i class="fas fa-user"></i>
				  	</div>
            <div>
              <input class="input" type="text" placeholder="Username" v-model="credentials.username" v-on:keyup="enterKeyToSubmit">
					  </div>

          </div>

          <div class="input-div two focus">

            <div class="i">
              <i class="fas fa-lock"></i>
            </div>

            <div>
              <input class="input" type="password" placeholder="Password" v-model="credentials.password" v-on:keyup="enterKeyToSubmit">
            </div>

          </div>

          <button type="button" class="btn" :class="{ 'is-loading': isLoading }" @click="submit()">
                Login
          </button>
        </form>
    </div>
  </div>



</template>

<script>
  import auth from 'src/shared/auth'
  import bus from 'src/shared/bus'

  export default {
    data () {
      return {
        // We need to initialize the component with any
        // properties that will be used in it
        auth: auth,
        credentials: {
          username: '',
          password: ''
        },
        error: '',
        isLoading: false
      }
    },
    methods: {
      enterKeyToSubmit: function (e) {
        if (e.keyCode === 13) {
          this.submit()
        }
      },
      submit () {
        bus.$emit('SHOW_LOADING')
        var credentials = {
          username: this.credentials.username,
          password: this.credentials.password
        }

        // We need to pass the component's this context
        // to properly make use of http in the auth service

        this.isLoading = true
        this.auth.login(this, credentials, '/home')
        bus.$emit('HIDE_LOADING')
        this.isLoading = false
      }
    },
    created () {
      console.log('LOGIN CREATED')
      console.log(this.auth)
    }
  }
</script>

<style lang="scss">

*{
	padding: 0;
	margin:0;
	box-sizing: border-box;
}

#line{
  position: relative;
  margin-top: -45px;
  height: 50px;
  width: 100%;
  background-color: #fff;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.container.login{
	width: 100vw;
	height: 100vh;
	display: grid;
	grid-template-columns: repeat(2, 1fr);
	grid-gap: 7rem;
	padding: 0 2rem;
}

.background-login{
	display: flex;
	justify-content: flex-end;
	align-items: center;
}

.background-login img {
	width: 800px;
}

.login-container{
	display: flex;
	align-items: center;
	text-align: center;
}

.login-container form{
	width: 360px;
}

.avatar{
	 width: 200px;
	 margin-bottom: 15%;
}

.login-container form h2{
  	font-weight: bold;
	font-size: 2.9rem;
	text-transform: uppercase;
	margin-top: -40px;
	color:#333333;
}

.login-container form h3{
	font-size: 1.8rem;
	text-transform: uppercase;
	color:#474747;
}

.login-container .input-div{
	position: relative;
	display: grid;
	grid-template-columns: 7% 93%;
	margin: 25px 0;
	padding: 5px 0;
	border-bottom: 2px solid #d9d9d9d9;
}

.login-container .input-div:after, .input-div:before{
	content: '';
	position: absolute;
	bottom: -2px;
	width: 0;
	height: 2px;
	background-color: #FFC107;
	transition: .3s;
}

.login-container .input-div:after{
	right: 50%;
}

.login-container .input-div:before{
	left: 50%;
}

.login-container .input-div.focus .i i{
	color: #FFC107;
}

.login-container .input-div.focus:after, .input-div.focus:before{
	width: 50%;
}

.login-container .input-div.one{
	margin-top: 10%;
}
.login-container .input-div.two{
	margin-top: 4px;
}

.login-container .i{
	display: flex;
	justify-content: center;
	align-items: center;
}

.login-container .i i{
	color: #d9d9d9d9;
	transition: .3s;
}

.login-container .input-div > div{
	position: relative;
	height: 45px;
}

.login-container .input-div > div h5{
	position: absolute;
	left: 10px;
	top: 50%;
	transform: translateY(-50%);
	color: #999;
	font-size: 18px;
	transition: .3s;
}

.login-container .input{
	position: absolute;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	border: none;
	outline: none;
	background: none;
	padding: 0.5rem 0.7rem;
 	font-size: 1.2rem;
 	font-family: 'Poppins', sans-serif;
 	color: #555;
}

.login-container a{
	display: block;
	text-align: right;
	text-decoration: none;
	color: #000000;;
	font-size: 0.9rem;
	transition: .3s;
}

.login-container a:hover{
	color: #FFC107;
}

.login-container .btn{
	display: block;
	width: 100%;
	height: 50px;
	border-radius: 25px;
	margin: 1rem 0;
	font-size: 1.2rem;
	outline: none;
	border:none;
	text-transform: uppercase;
	background-image: linear-gradient(to right, #E6AD00,#FFC107, #E6AD00);
	cursor: pointer;
	color: #fff;
	font-family: 'Poppins',sans-serif;
	background-size: 200%;
	transition: .5s;
}

.login-container .btn:hover{
	 background-position: right;
}

@media screen and (max-width: 1050px){
	.container.login{
		grid-gap: 5rem;
	}
}

@media screen and (max-width: 1000px){
	.login-container form{
		width: 290px;
	}

	.login-container form h2{
		font-size: 2.4rem;
		margin: 8px 0;
	}

	.background-login img{
		width: 400px;
	}
}

@media screen and (max-width: 900px){
	.background-login{
		display: none;
	}

	.container.login{
		grid-template-columns: 1fr;
	}

	.login-container{
		justify-content: center;
	}

	.login-container form{
		margin-right: 135px;
	}
}
</style>
