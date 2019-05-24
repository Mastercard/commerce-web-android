package com.us.masterpass.merchantapp;


import com.us.masterpass.merchantapp.domain.model.SettingsVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contain common parameters for Settings related test cases.
 */
abstract class AbsSettingsTest extends AbsTest {

    /**
     * @return A @{@link List<SettingsVO>}
     */
    List<SettingsVO> getSettingsVoList() {
        List<SettingsVO> list = new ArrayList<>();
        list.add(getSettingsVo());
        return list;
    }

    /**
     *
     * @return A @{@link SettingsVO}
     */
    SettingsVO getSettingsVo() {
        return new SettingsVO();
    }
}
