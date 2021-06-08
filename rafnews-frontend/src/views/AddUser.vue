<template>
  <div class="add-user">
    <div class="container">
      <h1>Add a new user</h1>
      <form>
        <div class="form-group"><label>Name:</label><input v-model="user.name" type="text" class="form-control" /></div>
        <div class="form-group"><label>Surname:</label><input v-model="user.surname" type="text" class="form-control" /></div>
        <div class="form-group"><label>E-mail:</label><input v-model="user.email" type="text" class="form-control" /></div>
        <div class="form-group"><label>Role:</label><select v-model="user.role" class="form-control">
          <option value="ADMIN" selected>Admin</option>
          <option value="CONTENT_CREATOR">Content Creator</option>
        </select></div>
        <div class="form-group"><label>Password:</label><input v-model="user.password" class="form-control" type="password" /></div>
        <div class="form-group"><label>Password confirmation:</label><input v-model="user.passwordConfirmation" class="form-control" type="password" /></div>
      </form>
    </div>
    <button @click="addUser" type="button" class="btn btn-primary">Add user</button>
  </div>
</template>

<script>
export default {
  name: "AddUser",
  data() {
    return {
      user: {
        name: "",
        surname: "",
        email: "",
        role: "",
        password: "",
        passwordConfirmation: ""
      }
    }
  },
  methods: {
    addUser() {
      if (this.user.name.trim() && this.user.surname.trim() && this.user.email.trim()
        && this.user.role.trim() && this.user.password && this.user.passwordConfirmation) {

        if (!this.user.email.match(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)) {
          alert('Please enter a valid email address')
          return
        }

        if (this.user.password != this.user.passwordConfirmation) {
          alert('Passwords do not match')
          return
        }

        this.$axios.post('/api/user/add', {
          name: this.user.name,
          surname: this.user.surname,
          email: this.user.email,
          role: this.user.role,
          password: this.user.password
        })
        .then(response => {
          if (response.data.status == "SUCCESS") {
            alert('User added successfully')
            this.$router.push('/cms')
          } else {
            alert(response.data.message);
          }
        })

      } else {
        alert('Please fill in all fields')
      }
    }
  }
}
</script>

<style scoped>

</style>