<template>
<div>
  <h1>Inventory</h1>
</div>
</template>

<script>
import {useAppStore} from "@/store/app";
import {mapStores} from "pinia";

export default {
  name: 'InventoryView',
  computed: {
    ...mapStores(useAppStore),
  },
  data() {
    return {
      tab: null,
    };
  },
  methods: {},
}
</script>
