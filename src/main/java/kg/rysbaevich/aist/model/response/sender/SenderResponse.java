package kg.rysbaevich.aist.model.response.sender;

import kg.rysbaevich.aist.model.dto.sender.SenderDto;

import java.util.List;

public record SenderResponse(
        int size,
        List<SenderDto> senderDtoList
) {
}
