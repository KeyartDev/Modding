# Подсказки
## Предмет
### Создание
- Добавить класс-регистратор и зарегестрировать его в основном классе мода;
- Зарегистрировать предмет;
- Добавить перевод;
- Добавить модель и текстуру.
> Формат json-файла модели предмета выглядит так:
> ```json
> {
>   "parent": "item/generated",
>   "textures": {
>     "layer0": "MOD_ID:block/item"
>   }
> }
> ```
### Свойства предмета
  У предмета есть следующие свойства:
- stacksTo(int)$
- fireResistant();
- rarity(Rarity);
- durability(int);
- craftReminder(Item);
- defaultDurability(int);
- food(FoodProperies);
- setNoRepair().
## Блок
### Создание
- Добавить класс-регистратор и зарегестрировать его в основном классе мода;
- Зарегистрировать блок и предмет-блок(класс BlockItem);
- Добавить перевод;
- Добавить модель и текстуру;
- Добавить состояния блока(assets/MOD_ID/blockstates).
> Формат json-файла модели блока, если все стороны имеют одинаковую текстуру:
> ```json
> {
>   "parent": "minecraft:block/cube_all",
>   "textures": {
>     "all": "MOD_ID:block/block"
>   }
> }
> ```
> Формат json файла бока с 1 состоянием:
> ```json
> {
>   "variants": {
>     "": {
>       "model": "MOD_ID:block/block"
>     }
>   }
> }
> ```

> [!TIP]
> Для удобства можно сделать функции-помощники, которые будут регестрировать и блок, и предемет сразу:
> ```java
> public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
>     RegistryObject<T> toReturn = BLOCKS.register(name, block);
>     registerBlockItem(name, toReturn);
>     return toReturn;
> }
>
> public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
>     return ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
> }
> ```
### Свойства блока
  У блока есть следующие свойства:
- noParticlesOnBreak();
- requiredCorrectToolForDrops();
- noCollision();
- noOcclusion();
- sound(SoundType);
- destroyTime(float);
- air();
- dynamicShape();
- emissiveRendering(StatePredicate);
- explosionResistance(float);
- forceSolidOn();
- friction();
- ignitedByLava();
- instabreak();
- instrument(NoteBlockInstrument);
- jumpFactor(float);
- lightLevel(ToIntFunction<BlockState>);
- lootFrom(Supplier<? extends Block>);
- mapColor(DyeColor/MapColor);
- noLootTable();
- offSetType(OffsetType);
- pushReaction(PushReaction);
- randomTicks();
- replacable();
- speedFactor(float).
