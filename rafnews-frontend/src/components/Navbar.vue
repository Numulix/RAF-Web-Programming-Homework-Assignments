<template>
  <nav class="navbar navbar-light navbar-expand-md navigation-clean">
    <div class="container"><a class="navbar-brand" href="#">RAF News</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
      <div class="collapse navbar-collapse" id="navcol-1">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item"><a class="nav-link active" href="/">Home Page</a></li>
          <li class="nav-item"><a class="nav-link active" href="/popular">Most read</a></li>
          <li class="nav-item"></li>
          <li class="nav-item dropdown"><a aria-expanded="false" data-toggle="dropdown" class="dropdown-toggle nav-link" href="#">Categories</a>
            <div class="dropdown-menu">
              <div v-for="category in categories" :key="category.id">
                <a class="dropdown-item" :href="'/category/'+category.id">{{ category.categoryName }}</a>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "Navbar",
  data() {
    return {
      categories: []
    }
  },
  created() {
    this.$axios.get('/api/category')
    .then(response => {
      if (response.data.status == "SUCCESS") {
        this.categories = response.data.data
      }
    })
  }
}
</script>

<style scoped>

</style>