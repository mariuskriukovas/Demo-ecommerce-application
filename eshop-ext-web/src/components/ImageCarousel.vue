<template>
  <v-carousel v-if="readonly" v-model="activeIndex" hide-delimiters show-arrows="hover">
    <template v-if="hasItems">
      <v-carousel-item
        v-for="(item,i) in items"
        :key="i"
      >
        <v-sheet
          border="md"
          class="pa-6 text-white mx-auto"
          height="500px"
          width="600px"
        >
          <v-img
            :src="item.s3Url"
            aspect-ratio="16/9"
            cover
            max-height="500px"
          ></v-img>
        </v-sheet>
      </v-carousel-item>
    </template>
    <template v-else>
      <v-carousel-item>
        <v-sheet
          border="md"
          class="pa-6 text-white mx-auto"
          height="500px"
          width="600px"
        >
          <v-img
            :src="DEFAULT_IMAGE_URL"
            aspect-ratio="16/9"
            cover
            max-height="450px"
          ></v-img>
        </v-sheet>
      </v-carousel-item>
    </template>
  </v-carousel>
  <v-carousel v-else v-model="activeIndex" hide-delimiters show-arrows="hover">
    <v-carousel-item
      v-for="(item,i) in items"
      :key="i"
    >
      <v-sheet
        border="md"
        class="pa-6 text-white mx-auto"
        height="500px"
        width="600px"
      >
        <v-img
          :src="item.s3Url"
          aspect-ratio="16/9"
          cover
          max-height="400px"
        ></v-img>
        <v-btn
          block
          class="text-none text-black mt-2"
          color="red-accent-2"
          size="x-large"
          variant="outlined"
          @click="removeImage(item)"
        >
          Remove Image
        </v-btn>
      </v-sheet>
    </v-carousel-item>
    <v-carousel-item>
      <v-sheet
        border="md"
        class="pa-6 text-white mx-auto"
        height="500px"
        width="600px"
      >
        <v-row align="center" justify="center" style="height: 100%">
          <v-col>
            <v-btn
              block
              class="text-none text-black mt-2"
              color="green-accent-2"
              size="x-large"
              variant="outlined"
              @click="addImages"
            >
              Add Images
            </v-btn>
          </v-col>
        </v-row>
      </v-sheet>
    </v-carousel-item>
  </v-carousel>
  <v-dialog
    v-if="!readonly"
    v-model="isCreateModalVisible"
    min-width="800px"
    width="auto"
  >
    <v-card class="mt-2 ml-2 mr-2 mb-2" title="Add Product Files">
      <v-row class="ml-2 mr-2">
        <v-col cols="12">
          <v-file-input v-model="files" chips clearable label="Files" multiple variant="underlined"></v-file-input>
        </v-col>
      </v-row>
      <v-row class="mb-2 ml-2 mr-2">
        <v-col class="text-right mr-2">
          <v-btn class="text-right mr-2" color="primary" outlined @click="onClose">
            Close
          </v-btn>
          <v-btn class="text-right" color="primary" outlined @click="onAdd">
            Add
          </v-btn>
        </v-col>
      </v-row>
    </v-card>
  </v-dialog>
</template>

<script>
import {useAppStore} from "@/store/app";
import {mapActions, mapStores} from "pinia";
import {cloneDeep, isArray, isEqual} from 'lodash';
import ProductFileApi from "@/services/ProductFileApi";
import {useSnackbarStore} from "@/store/snackbars";
import ProductApi from "@/services/ProductApi";
import {DEFAULT_IMAGE_URL} from "@/utils/imageUtil";

export default {
  name: 'ImageCarousel',
  components: {},
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
    refresh: {
      type: Function,
      required: false,
      default() {
        return async () => {
        }
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
    hasItems() {
      return this.items.length > 0
    },
  },
  data() {
    return {
      itemsVal: [],
      activeIndex: 0,
      isCreateModalVisible: false,
      files: [],
      DEFAULT_IMAGE_URL
    };
  },
  methods: {
    ...mapActions(useSnackbarStore, ['openSnackbar']),

    async removeImage(item) {
      if (confirm('Are you really want to delete this image ?')) {
        const successMessage = await ProductFileApi.deleteProductFile(item.id)
        this.openSnackbar(successMessage)
        this.refresh()
      }
    },
    addImages() {
      this.isCreateModalVisible = true
    },
    onClose() {
      this.isCreateModalVisible = false
    },
    async onAdd() {
      const {id} = this.$route.params
      const successMessage = await ProductApi.uploadProductFiles(id, this.files)
      if (successMessage) {
        this.openSnackbar(successMessage)
        this.onClose()
        await this.refresh()
      }
    },
  },
}
</script>
