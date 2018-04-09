package pl.jakubkozlowski.learning.firststeps.mapper;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jakubkozlowski.learning.firststeps.model.Champion;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
//For autoincrement reset between tests
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ChampionMapperTest {

    private static final String AATROX = "Aatrox";
    private static final String AHRI = "Ahri";
    private static final Long id1 = 1L;
    private static final Long id2 = 2L;
    @Autowired
    private ChampionMapper championMapper;
    private Champion expectedAatrox;
    private Champion expectedAhri;


    @Before
    public void setUp() throws Exception {
        expectedAatrox = new Champion(id1, AATROX);
        expectedAhri = new Champion(id2, AHRI);
    }

    @Test
    public void whenFindOne_thenReturnChampion() {
        //given
        championMapper.persist(expectedAatrox);
        //when
        Champion actual = championMapper.findById(id1);
        //then
        assertThat(actual)
                .isEqualTo(expectedAatrox);
    }

    @Test
    public void whenFindAll_thenReturnChampionList(){
        //given
        championMapper.persist(expectedAatrox);
        championMapper.persist(expectedAhri);
        //when
        List<Champion> actual = championMapper.findAll();
        //then
        assertThat(actual.size())
                .isEqualTo(2);
        assertThat(actual.get(0))
                .isEqualTo(expectedAatrox);
        assertThat(actual.get(1))
                .isEqualTo(expectedAhri);
    }

    @Test
    public void whenPersist_thenIdAutoincrementsAndReturnsChampion(){
        //when
        championMapper.persist(expectedAhri);
        //then
        assertThat(championMapper.findByName(expectedAhri.getName()))
                .isEqualTo(new Champion(id1, AHRI));

    }

    @Test(expected = DuplicateKeyException.class)
    public void whenPersistWithTheSameName_thenExceptionOccur(){
        //when
        championMapper.persist(expectedAhri);
        championMapper.persist(expectedAhri);
        //then

    }

    @Test
    public void whenUpdate_thenUpdateChampionEntity(){
        //given
        championMapper.persist(expectedAatrox);
        //when
        championMapper.update(id1, expectedAhri);
        Champion prev = championMapper.findById(id1);
        Champion actual = championMapper.findById(id2);
        //then
        assertThat(prev).isNull();
        assertThat(actual)
                .isEqualTo(expectedAhri);

    }

    @Test(expected = DuplicateKeyException.class)
    public void whenUpdateToUsedId_thenExceptionOccurs(){
        //given
        championMapper.persist(expectedAatrox);
        championMapper.persist(expectedAhri);
        //when
        championMapper.update(id1, new Champion(id2, "Mark"));
        //then
        //DuplicateKeyException throws
    }

    @Test(expected = DuplicateKeyException.class)
    public void whenUpdateToUsedName_thenExceptionOccurs(){
        //given
        championMapper.persist(expectedAatrox);
        championMapper.persist(expectedAhri);
        //when
        championMapper.update(id1, new Champion(id1, "Ahri"));
        //then
        //DuplicateKeyException throws
    }

    @Test
    public void whenDeleteById_theDeleteChampion(){
        //given
        championMapper.persist(expectedAatrox);
        Champion previous = championMapper.findById(id1);
        assertThat(previous)
                .isEqualTo(expectedAatrox);
        //when
        championMapper.deleteById(id1);
        Champion actual = championMapper.findById(id1);
        //then
        assertThat(actual).isNull();
    }

}