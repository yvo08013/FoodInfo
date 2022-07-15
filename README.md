# NOTES
- в layout ничего не рефакторил, как сделал изначально так и оставил, там скорее всего куча вещей которые можно сделать лучше/по другому (например кнопка back, сверху слева, есть на многих фрагментах, её можно вынести в отдельный layout)
- не нравится как передается контекст в RecyclerViewAdapter, думаю лучше его передавать через Inject, но как именно - не знаю
- в превью криво отображаются элементы RecyclerView (приклеены друг к другу) потому что отступы для них задаются программно, через RecyclerView.ItemDecoration, как реализовать через layout именно такое поведение отступов я не не знаю
- при очень быстром переключении между вкладками home и explore в home из-за анимации перестановки в RecyclerView элементы могут встать криво
- отсутствует пагинация (из бд в RecyclerView летит сразу всё) в будущем будет исправлено
- есть несколько мест, где лучше бы сделать базовый класс и не дублировать код (например Range и Nutrient TypeConverter)
- практически отсутствуют комментарии (планирую в скором времени исправить, пока не поздно и кода не много)
- в бд есть пару заглушек в виде allowMainThreadQueries() и CoroutineContext, сделаны на скорую руку лиж бы запустить и протестить бд (запросы, правильность аннотаций, конвертеры и т.д.)
- кроме RawQuery для фильтра так ничего в голову не пришло (про БД детально распишу в комментариях)
- фрагменты не сохраняют состояния своих RecyclerView, в будущем будет исправлено
- весь код в UI потоке (до многопоточности еще руки не дошли)

Все "что это такое" и "так никто не делает" моменты которые я не описал в NOTES лежат на моей совести
