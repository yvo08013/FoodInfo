# NOTES
- в layout ничего не рефакторил, как сделал изначально так и оставил, там скорее всего куча вещей которые можно сделать лучше/по другому (например кнопка back, сверху слева, есть на многих фрагментах, её можно вынести в отдельный layout)
- в превью криво отображаются элементы RecyclerView (приклеены друг к другу) потому что отступы для них задаются программно, через RecyclerView.ItemDecoration, как реализовать через layout именно такое поведение отступов я не не знаю
- есть несколько мест, где лучше бы сделать базовый класс и не дублировать код (например Range и Nutrient TypeConverter)
- вложенные RecyclerView в Explore стучатся в бд при каждом onViewAttachedToWindow (причем дважды). Не знаю как пофиксить, В остальном Explore фрагмент работает так, как я хочу