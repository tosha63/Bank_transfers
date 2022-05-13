package shtanko.model;

import shtanko.dto.TransferDto;
import shtanko.entity.transfer.Transfer;

public interface Transferable {
    Transfer createTransfer(TransferDto transferDto);
}
