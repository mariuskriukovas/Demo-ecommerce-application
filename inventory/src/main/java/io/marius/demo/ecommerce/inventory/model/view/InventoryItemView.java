package io.marius.demo.ecommerce.inventory.model.view;


public class InventoryItemView {
    Long id;
    String name;
    Double price;
    Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public static final class InventoryItemViewBuilder {
        private final InventoryItemView inventoryItemView;

        private InventoryItemViewBuilder() {
            inventoryItemView = new InventoryItemView();
        }

        public static InventoryItemViewBuilder anInventoryItemView() {
            return new InventoryItemViewBuilder();
        }

        public InventoryItemViewBuilder id(Long id) {
            inventoryItemView.setId(id);
            return this;
        }

        public InventoryItemViewBuilder name(String name) {
            inventoryItemView.setName(name);
            return this;
        }

        public InventoryItemViewBuilder price(Double price) {
            inventoryItemView.setPrice(price);
            return this;
        }

        public InventoryItemViewBuilder quantity(Integer quantity) {
            inventoryItemView.setQuantity(quantity);
            return this;
        }

        public InventoryItemView build() {
            return inventoryItemView;
        }
    }
}
