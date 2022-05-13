package shtanko.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import shtanko.dto.TransferDto;
import shtanko.entity.card.Card;
import shtanko.entity.card.DebitCard;
import shtanko.entity.transfer.Transfer;
import shtanko.entity.user.User;
import shtanko.exception.NoSuchCardException;
import shtanko.repository.TransferRepository;
import shtanko.repository.UserRepository;
import shtanko.type.TransferStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @InjectMocks
    private TransferService transferService;

    @Mock
    protected TransferRepository transferRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    protected TransferDto transferDto;

    @Mock
    protected User user;

    @Test
    void getAllTest() {
        when(transferRepository.findAll()).thenReturn(Collections.singletonList(new Transfer()));
        List<Transfer> transfers = transferService.getAll();
        assertEquals(1, transfers.size());
        verify(transferRepository, times(1)).findAll();
    }

    @Test
    void getUserTest() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByLogin(anyString())).thenReturn(user);
        assertEquals(user, transferService.getUser());
        verify(userRepository, times(1)).findByLogin("user");
    }


    @Test
    void getTransferTest() {
        when(transferDto.getSumTransfer()).thenReturn(1000L);
        Transfer transfer = transferService.getTransfer(transferDto, user);
        assertEquals(1000L, transfer.getSumTransfer());
        assertEquals(user, transfer.getUser());
        assertEquals(TransferStatus.UNBLOCK, transfer.getTransferStatus());
        verify(transferDto, times(1)).getSumTransfer();
    }

    @Test
    void getCardThrowExceptionTest() {
        List<Card> cards = new ArrayList<>();
        when(user.getCards()).thenReturn(cards);
        assertThrows(NoSuchCardException.class, () -> transferService.getCard(transferDto, user));
    }

    @Test
    void getCardTest() {
        Card cardTest = new DebitCard();
        cardTest.setNumberCard(1234567890000000L);
        List<Card> cards = new ArrayList<>();
        cards.add(cardTest);
        when(user.getCards()).thenReturn(cards);
        when(transferDto.getWithdrawNumberCard()).thenReturn(1234567890000000L);
        Card card = transferService.getCard(transferDto, user);
        assertEquals(1234567890000000L, card.getNumberCard());
    }


    @Test
    void getTransferByIdTest() {
        Transfer tempTransfer = new Transfer();
        tempTransfer.setSumTransfer(1000L);
        when(transferRepository.findById(anyLong())).thenReturn(Optional.of(tempTransfer));
        Transfer transfer = transferService.getTransferById(anyLong());
        assertEquals(tempTransfer.getSumTransfer(), transfer.getSumTransfer());
    }

    @Captor
    private ArgumentCaptor<Transfer> argumentCaptor;

    @Test
    void saveTransferTest() {
        Transfer transfer = new Transfer();
        transfer.setSumTransfer(1000L);
        transferService.saveTransfer(transfer);
        verify(transferRepository).save(argumentCaptor.capture());
        Transfer transferCaptor = argumentCaptor.getValue();
        assertEquals(transfer.getSumTransfer(), transferCaptor.getSumTransfer());
    }
}