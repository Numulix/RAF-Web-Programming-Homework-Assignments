<template>
  <div class="login">
    <section class="login-clean">
      <form method="post">
        <h2>RAF News CMS</h2>
        <h3>Login</h3>
        <div class="form-group"><input v-model="email" class="form-control" type="email" name="email" placeholder="Email" /></div>
        <div class="form-group"><input v-model="password" class="form-control" type="password" name="password" placeholder="Password" /></div>
        <div class="form-group"><button @click="login" class="btn btn-primary btn-block" type="button">Log In</button></div>
      </form>
    </section>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      email: "",
      password: ""
    }
  },
  methods: {
    login() {
      if (this.email.trim() && this.password.trim()) {
        this.$axios.post(`/api/user/login`, {
          email: this.email,
          password: this.password
        })
        .then(response => {
          if (response.data.status == "SUCCESS") {
            localStorage.setItem('jwt', response.data.data.jwt);
            this.$router.push('/cms')
          }
        })
        .catch(err => {
          alert("Invalid credentials")
        })
      }
    }
  }
}
</script>

<style scoped>
.login-clean {
  background: #f1f7fc;
  padding: 80px 0;
}

.login-clean form {
  max-width: 320px;
  width: 90%;
  margin: 0 auto;
  background-color: #ffffff;
  padding: 40px;
  border-radius: 4px;
  color: #505e6c;
  box-shadow: 1px 1px 5px rgba(0,0,0,0.1);
}

.login-clean .illustration {
  text-align: center;
  padding: 0 0 20px;
  font-size: 100px;
  color: rgb(244,71,107);
}

.login-clean form .form-control {
  background: #f7f9fc;
  border: none;
  border-bottom: 1px solid #dfe7f1;
  border-radius: 0;
  box-shadow: none;
  outline: none;
  color: inherit;
  text-indent: 8px;
  height: 42px;
}

.login-clean form .btn-primary {
  background: #f4476b;
  border: none;
  border-radius: 4px;
  padding: 11px;
  box-shadow: none;
  margin-top: 26px;
  text-shadow: none;
  outline: none !important;
}

.login-clean form .btn-primary:hover, .login-clean form .btn-primary:active {
  background: #eb3b60;
}

.login-clean form .btn-primary:active {
  transform: translateY(1px);
}

.login-clean form .forgot {
  display: block;
  text-align: center;
  font-size: 12px;
  color: #6f7a85;
  opacity: 0.9;
  text-decoration: none;
}

.login-clean form .forgot:hover, .login-clean form .forgot:active {
  opacity: 1;
  text-decoration: none;
}
</style>