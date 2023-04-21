<template>
  <v-card>
    <v-tabs v-model="activeTab" color="deep-purple-accent-4"
            grow>
      <v-tab v-for="tab of tabs" :key="tab.id" :to="tab.route" exact>
        {{ tab.name }}
      </v-tab>
    </v-tabs>
    <router-view/>
  </v-card>
</template>

<script>
export default {
  name: 'HomeView',
  data() {
    return {
      activeTab: `/home`,
      tabs: [
        {id: 1, name: "Inventory", route: `/home`},
        {id: 2, name: "Products", route: `/home/products`}
      ]
    };
  }
};
</script>
