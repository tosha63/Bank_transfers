package shtanko.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void displayTransferHistoryUsers() throws Exception {
        mockMvc.perform(get("/admin/transferHistoryUsers"))
                .andExpect(view().name("transfers/adminTransfers"))
                .andExpect(content().string(
                        containsString("Дата 11.05.2022 09:22:12 перевод совершил Андреев Андрей сумма перевода 40000 на карту 5555555555555555")));
    }

}