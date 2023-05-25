<template>
  <v-card class="ml-2 mr-2" title="Properties">
    <v-row v-if="!readonly" class="ml-2 mr-2 mt-2 mb-2" justify='end'>
      <v-icon
        class="mr-2"
        color="primary"
        icon="mdi-plus"
        size="large"
        @click="addItem"
      ></v-icon>
      <v-icon
        class="mr-2"
        color="primary"
        icon="mdi-minus"
        size="large"
        @click="removeItem"
      ></v-icon>
    </v-row>
    <template v-for="(item, index) in items" :key="index">
      <v-row v-if="readonly" class="ml-2 mr-2">
        <v-col cols="6">
          <v-text-field v-model="item['name']" label="Name" readonly variant="underlined"></v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field v-model="item['description']" label="Description" readonly
                        variant="underlined"></v-text-field>
        </v-col>
      </v-row>
      <v-row v-else class="ml-2 mr-2">
        <v-col cols="5">
          <v-text-field v-model="item['name']" :readonly="readonly" label="Name" variant="underlined"></v-text-field>
        </v-col>
        <v-col cols="5">
          <v-text-field v-model="item['description']" :readonly="readonly" label="Description"
                        variant="underlined"></v-text-field>
        </v-col>
        <v-col align="right" cols="2">
          <v-icon
            class="mr-2"
            color="white-darken-2"
            icon="mdi-delete"
            size="large"
            @click="removeByIndex(index)"
          ></v-icon>
        </v-col>
      </v-row>
    </template>
  </v-card>
</template>

<script>
import {useAppStore} from "@/store/app";
import {mapStores} from "pinia";
import {cloneDeep, isArray, isEqual} from 'lodash';

export default {
  name: 'PropertiesInput',
  props: {
    modelValue: {
      type: Array,
      required: true,
      default() {
        return []
      }
    },
    readonly: {
      type: Boolean,
      required: false,
      default() {
        return false
      }
    },
  },
  watch: {
    modelValue: function (newVal) {
      if (!isEqual(newVal, this.items)) {
        this.items = newVal
      }
    }
  },
  mounted() {
    this.items = cloneDeep(this.modelValue)
  },
  computed: {
    ...mapStores(useAppStore),
    items: {
      get() {
        return this.itemsVal
      },
      set(newValue) {
        if (isArray(newValue)) {
          this.itemsVal = newValue
        } else {
          this.itemsVal = []
        }
        this.$emit('update:modelValue', this.itemsVal);
      }
    },
  },
  data() {
    return {
      itemsVal: [],
    };
  },
  methods: {
    addItem() {
      this.items.push({name: "", description: ""})
    },
    removeItem() {
      this.items.pop()
    },
    removeByIndex(idx) {
      this.items.splice(idx, 1);
    }
  },
}
</script>
