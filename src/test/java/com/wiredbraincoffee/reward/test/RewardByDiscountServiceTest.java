package com.wiredbraincoffee.reward.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.wiredbraincoffee.product.Product;
import com.wiredbraincoffee.reward.RewardByDiscountService;
import com.wiredbraincoffee.reward.RewardInformation;

public class RewardByDiscountServiceTest {
    private RewardByDiscountService reward = null;

    @BeforeEach
    void setUp() {
        reward = new RewardByDiscountService();
        reward.setPercentage(0.1);
        reward.setNeededPoints(100);
    }

    @Test
    @DisplayName("When customer has zero points no reward should be applied")
    void zeroCustomerPoints() {
        Product smallDecaf = new Product(1, "Small Decaf", 1.99);
        List<Product> order = Collections.singletonList(smallDecaf);

        RewardInformation info = reward.applyReward(order, 0);

        assertEquals(0, info.getDiscount());
        assertEquals(0, info.getPointsRedeemed());
    }

    @Test
    @DisplayName("When customer has enough points reward should be applied")
    void customerHasEnoughPoints() {
        Product smallDecaf = new Product(1, "Small Decaf", 1.99);
        List<Product> order = Collections.singletonList(smallDecaf);

        RewardInformation info = reward.applyReward(order, 100);
        
        assertEquals(0.199, info.getDiscount());
        assertEquals(reward.getNeededPoints(), info.getPointsRedeemed());
    }
    
    @Test
    @DisplayName("Exception is thrown when invalid percentage is set")
    void exceptionThrownWhenInvalidPercentage() {
        long percentage = 0;
        assertThrows(RuntimeException.class, () -> {
        	reward.setPercentage(percentage);
        });
    }
}
