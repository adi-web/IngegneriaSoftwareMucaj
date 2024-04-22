package com.company.test.domainModel;

import com.company.domainModel.state.Delivered;
import com.company.domainModel.state.Ordered;
import com.company.domainModel.state.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class stateTest {
     private State state;
     @BeforeEach
    void setUp()
     {
         state= new Ordered();
     }

     @Test
    void testOrdered()
     {
         Assertions.assertEquals("ordered", state.getState());
     }

     @Test
    void changeState()
     {
         state=new Delivered();
         Assertions.assertEquals("delivered",state.getState());
     }
}
