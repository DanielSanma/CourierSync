package com.udea.couriersync.coverage;

import com.udea.couriersync.mapper.ClientMapperImpl;
import com.udea.couriersync.mapper.ShipmentMapperImpl;
import com.udea.couriersync.mapper.UserMapperImpl;
import com.udea.couriersync.mapper.VehicleMapperImpl;
import com.udea.couriersync.DTO.ClientDTO;
import com.udea.couriersync.DTO.ShipmentDTO;
import com.udea.couriersync.DTO.UserDTO;
import com.udea.couriersync.DTO.VehicleDTO;
import com.udea.couriersync.entity.Client;
import com.udea.couriersync.entity.Shipment;
import com.udea.couriersync.entity.User;
import com.udea.couriersync.entity.Vehicle;
import com.udea.couriersync.enums.ShipmentPriority;
import com.udea.couriersync.enums.ShipmentStatus;
import com.udea.couriersync.enums.UserRole;
import com.udea.couriersync.exception.BadRequestException;
import com.udea.couriersync.exception.ConflictException;
import com.udea.couriersync.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoverageBoostTest {

    @Test
    void dtoAndEntity_gettersSetters_andConstructors() {
        ClientDTO cdto = new ClientDTO(1L, "Name", "e@mail", "555", "addr");
        assertEquals(1L, cdto.getId());
        cdto.setName("Name2");
        assertEquals("Name2", cdto.getName());

        VehicleDTO vdto = new VehicleDTO(2L, "PLT", "Model", 1000.0, true);
        assertTrue(vdto.isAvailable());
        vdto.setAvailable(false);
        assertFalse(vdto.isAvailable());

        UserDTO udto = new UserDTO(3L, "U", "u@u", "pwd", "p", UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, udto.getRole());
        udto.setRole(UserRole.DRIVER);
        assertEquals(UserRole.DRIVER, udto.getRole());

        ShipmentDTO sDto = new ShipmentDTO();
        sDto.setTrackingCode("T1");
        sDto.setStatus(ShipmentStatus.PENDIENTE);
        assertEquals("T1", sDto.getTrackingCode());

        Client c = new Client();
        c.setId(10L);
        c.setName("C");
        assertEquals(10L, c.getId());

        Vehicle v = new Vehicle();
        v.setPlate("ABC");
        assertEquals("ABC", v.getPlate());

        User u = new User();
        u.setEmail("x@x");
        assertEquals("x@x", u.getEmail());
    }

    @Test
    void mapperImplementations_mapBothWays() {
        ClientMapperImpl cm = new ClientMapperImpl();
        Client client = new Client();
        client.setId(5L);
        client.setName("Cli");
        client.setEmail("c@c");
        client.setPhone("123");
        client.setAddress("addr");
        ClientDTO cdto = cm.toDTO(client);
        assertNotNull(cdto);
        assertEquals("Cli", cdto.getName());
        Client client2 = cm.toEntity(cdto);
        assertNotNull(client2);
        assertEquals(cdto.getEmail(), client2.getEmail());

        VehicleMapperImpl vm = new VehicleMapperImpl();
        Vehicle ve = new Vehicle();
        ve.setId(7L);
        ve.setPlate("P1");
        ve.setModel("M");
        ve.setMaximumCapacity(200.0);
        ve.setAvailable(true);
        VehicleDTO vdto = vm.toDTO(ve);
        assertNotNull(vdto);
        assertEquals("P1", vdto.getPlate());
        Vehicle ve2 = vm.toEntity(vdto);
        assertNotNull(ve2);
        assertEquals(vdto.getModel(), ve2.getModel());

        UserMapperImpl um = new UserMapperImpl();
        User user = new User();
        user.setId(8L);
        user.setName("U1");
        user.setEmail("u1@u");
        user.setPassword("p");
        user.setPhone("9");
        user.setRole(UserRole.ADMIN);
        UserDTO udto2 = um.toDTO(user);
        assertNotNull(udto2);
        assertEquals("U1", udto2.getName());
        User u2 = um.toEntity(udto2);
        assertNotNull(u2);

        // No instantiation of ShipmentMapperImpl here to avoid dependencies
    }

    @Test
    void exceptions_and_enums_cover() {
        BadRequestException bre = new BadRequestException("bad");
        assertTrue(bre.getMessage().contains("bad"));
        ResourceNotFoundException rnfe = new ResourceNotFoundException("not");
        assertEquals("not", rnfe.getMessage());
        ConflictException ce = new ConflictException("conf");
        assertEquals("conf", ce.getMessage());

        // use enums
        ShipmentPriority[] sp = ShipmentPriority.values();
        assertTrue(sp.length >= 1);
        UserRole[] ur = UserRole.values();
        assertTrue(ur.length >= 1);
        ShipmentStatus[] ss = ShipmentStatus.values();
        assertTrue(ss.length >= 1);
    }
}
