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
@WithMockUser(username = "andrey", password = "andrey", authorities = "USER")
class TransferControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void displayTransferHistory() throws Exception {
        mockMvc.perform(get("/user/transferHistory"))
                .andExpect(view().name("transfers/transferHistory"))
                .andExpect(content().string(
                        containsString("Дата 11.05.2022 09:22:12 сумма перевода 40000 на карту 5555555555555555")));
    }

    @Test
    void changeTransfer() throws Exception {
        mockMvc.perform(get("/user/transfer"))
                .andExpect(view().name("transfers/transfer"))
                .andExpect(content().string(containsString("Перевод со счёта на счёт")))
                .andExpect(content().string(containsString("Перевод с карты на карту")))
                .andExpect(content().string(containsString("Перевод с карты на счёт")));
    }

    @Test
    void displayTransferCardToCard() throws Exception {
        mockMvc.perform(get("/user/transfer/card"))
                .andExpect(view().name("transfers/transferCard"))
                .andExpect(content().string(containsString("Введите номер карты, с которой хотите сделать перевод")))
                .andExpect(content().string(containsString("Введите номер карты, на который хотите сделать перевод")))
                .andExpect(content().string(containsString("Введите сумму перевода")));
    }


    @Test
    void displayTransferAccountToAccount() throws Exception {
        mockMvc.perform(get("/user/transfer/account"))
                .andExpect(view().name("transfers/transferAccount"))
                .andExpect(content().string(containsString("Введите номер счёта, с которого хотите сделать перевод")))
                .andExpect(content().string(containsString("Введите номер счёта, на который хотите сделать перевод")))
                .andExpect(content().string(containsString("Введите сумму перевода")));
    }


    @Test
    void displayTransferCardToAccount() throws Exception {
        mockMvc.perform(get("/user/transfer/cardToAcc"))
                .andExpect(view().name("transfers/transferCardToAcc"))
                .andExpect(content().string(containsString("Введите номер карты, с которой хотите сделать перевод")))
                .andExpect(content().string(containsString("Введите номер счёта, на который хотите сделать перевод")))
                .andExpect(content().string(containsString("Введите сумму перевода")));
    }

}