package uv.tc.apicupones.interfaces

import uv.tc.apicupones.pojo.Promocion

interface NotificarClic {
    fun seleccionarItem(posicion: Int, promocion: Promocion)
}