<template>
  <div>
    <h1>Products</h1>
  </div>
</template>

<script>
import {useAppStore} from "@/store/app";
import {mapStores} from "pinia";

export default {
  name: 'ProductView',
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
