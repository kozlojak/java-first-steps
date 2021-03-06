package pl.jakubkozlowski.leagueoflegends.restAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jakubkozlowski.leagueoflegends.restAPI.converter.ItemOrderConverter;
import pl.jakubkozlowski.leagueoflegends.restAPI.dto.ItemOrderDTO;
import pl.jakubkozlowski.leagueoflegends.restAPI.mapper.ItemOrderMapper;
import pl.jakubkozlowski.leagueoflegends.restAPI.model.ItemOrderEntity;

import java.util.List;
import java.util.Optional;

@Service
public class ItemOrderServiceImpl implements ItemOrderService {

    private ItemOrderMapper itemOrderMapper;
    private ItemOrderConverter ItemOrderConverter;

    @Autowired
    public ItemOrderServiceImpl(ItemOrderMapper itemOrderMapper, ItemOrderConverter ItemOrderConverter) {
        this.itemOrderMapper = itemOrderMapper;
        this.ItemOrderConverter = ItemOrderConverter;
    }

    @Override
    public Optional<List<ItemOrderDTO>> findAll() {
        return Optional.ofNullable(ItemOrderConverter.convertListEntity(itemOrderMapper.findAll()));
    }

    @Override
    public Optional<ItemOrderDTO> findById(Long id) {
        return Optional.ofNullable(ItemOrderConverter.convertEntity(itemOrderMapper.findById(id)));
    }

    @Override
    public ItemOrderDTO save(ItemOrderDTO itemOrderDTO) {
        ItemOrderEntity userEntity = ItemOrderConverter.convertDTO(itemOrderDTO);
        itemOrderMapper.save(userEntity);
        return ItemOrderConverter.convertEntity(userEntity);
    }

    @Override
    public void update(Long id, ItemOrderDTO itemOrderDTO) {
        itemOrderMapper.update(id, ItemOrderConverter.convertDTO(itemOrderDTO));
    }

    @Override
    public void deleteById(Long id) {
        itemOrderMapper.deleteById(id);
    }
}
