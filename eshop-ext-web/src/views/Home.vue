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
import router from "@/router";

export default {
  name: 'HomeView',
  async mounted() {
    const matchingTabs = this.tabs.filter(tab => this.$route.fullPath.includes(tab.route))
    if (matchingTabs.length === 0) {
      await router.push({path: this.tabs[0].route})
    } else if (matchingTabs.length === 1) {
      this.activeTab = matchingTabs[0].id
    } else {
      this.activeTab = matchingTabs[matchingTabs.length - 1].id
    }
  },
  data() {
    return {
      activeTab: `/home/inventory-items`,
      tabs: [
        {id: 0, name: "Inventory", route: `/home/inventory-items`}, // Todo use named path
        {id: 1, name: "Products", route: `/home/products`},
      ]
    };
  }
};
</script>
