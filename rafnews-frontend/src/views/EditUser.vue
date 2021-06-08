<template>
  <div class="edit-user">
    <div class="container">
      <h1>Edit user</h1>
      <form>
        <div class="form-group"><label>Name:</label><input v-model="user.name" type="text" class="form-control" /></div>
        <div class="form-group"><label>Surname:</label><input v-model="user.surname" type="text" class="form-control" /></div>
        <div class="form-group"><label>E-mail:</label><input v-model="user.email" type="text" class="form-control" /></div>
        <div class="form-group"><label>Role:</label><select v-model="user.role" class="form-control">
          <option value="ADMIN" selected>Admin</option>
          <option value="CONTENT_CREATOR">Content Creator</option>
        </select></div>
      </form>
    </div>
    <button @click="editUser" type="button" class="btn btn-primary">Add user</button>
  </div>
</template>

<script>
export default {
  name: "EditUser",
  data() {
    return {
      user: {}
    }
  },
  mounted() {
    this.$axios.get(`/api/user/${this.$route.params.id}`)
      .then(response => {
        if (response.data.status == "SUCCESS") {
          this.user = response.data.data
        }
      })
  },
  methods: {
    editUser() {
      if (this.user.name.trim() && this.user.surname.trim() && this.user.email.trim() && this.user.role.trim()) {

        if (!this.user.email.match(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)) {
          alert('Please enter a valid email address')
          return
        }

        this.$axios.put(`/api/user/${this.$route.params.id}`,{
          name: this.user.name,
          surname: this.user.surname,
          email: this.user.email,
          role: this.user.role,
          status: this.user.status
        })
        .then(response => {
          if (response.data.status == "SUCCESS") {
            alert('User edited successfully')
            this.$router.push('/cms')
          } else {
            alert(response.data.message)
          }
        })
        .catch(() => {
          alert('Email address already taken');
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