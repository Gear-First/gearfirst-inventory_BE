package com.gearfirst.backend.api.inbound.entity;

import com.gearfirst.backend.api.inventory.entity.Inventory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inbound_item")
public class InboundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "ibound_item_id")
    private Long id;

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private int price;
    @Column(name = "total_price" ,nullable = false)
    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="inbound_order_id", nullable = false)
    private InboundOrder inboundOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="inventory_id", nullable = false)
    private Inventory inventory;


    //생성자+빌더
    @Builder
    public InboundItem(InboundOrder inboundOrder, Inventory inventory, int quantity, int price) {
        validate(quantity, price, inventory, inboundOrder);
        this.inboundOrder = inboundOrder;
        this.inventory = inventory;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = calculateTotalPrice(price, quantity);
    }

    //유효성 검증
    private void validate(int quantity, int price, Inventory inventory, InboundOrder inboundOrder){
        if (quantity <= 0) throw new IllegalArgumentException("입고 수량은 0보다 커야 합니다.");
        if (price <= 0) throw new IllegalArgumentException("단가는 0보다 커야 합니다.");
        if (inventory == null) throw new IllegalArgumentException("입고 부품 정보가 필요합니다.");
        if (inboundOrder == null) throw new IllegalArgumentException("입고 명세서 정보가 필요합니다.");
    }
    //총액 자동 계산
    private int calculateTotalPrice(int price, int quantity){
        return price*quantity;
    }

    //수량이나 단가 변경 시, 총액 자동 계산
    public void updateQuantity(int newQuantity){
        if(newQuantity <= 0) throw new IllegalArgumentException("입고 수량은 0보다 커야 합니다.");
        this.quantity = newQuantity;
        this.totalPrice = calculateTotalPrice(this.price, newQuantity);
    }
    public void updatePrice(int newPrice){
        if (price <= 0) throw new IllegalArgumentException("단가는 0보다 커야 합니다.");
        this.price = newPrice;
        this.totalPrice = calculateTotalPrice(newPrice, this.quantity);
    }

    //입고 완료시 재고 반영용 데이터
    public int getInboundQuantity(){
        return this.quantity;
    }
    public Inventory getInboundForUpdate(){
        return this.inventory;
    }
}
