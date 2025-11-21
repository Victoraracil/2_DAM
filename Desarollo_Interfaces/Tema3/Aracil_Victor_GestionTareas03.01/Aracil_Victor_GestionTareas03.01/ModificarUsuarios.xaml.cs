using Aracil_Victor_GestionTareas03._01.Migrations;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using TaskManager.Data.Models;

namespace Aracil_Victor_GestionTareas03._01
{
    /// <summary>
    /// Lógica de interacción para ModificarUsuarios.xaml
    ///<author> Victor Aracil Gozalvez</author>
    /// </summary>
    public partial class ModificarUsuarios : Window
    {
        private List<User> todosUsuarios = new(); // Lista completa no observable
        public ObservableCollection<User> UsuariosVisibles { get; } = new();  // Lo que se muestra en UI

        // Copia temporal que se usa en el panel de detalle para editar sin afectar la colección original
        private User? _editingUser;

        public ModificarUsuarios()
        {
            InitializeComponent();

            this.DataContext = this;

            _ = CargarUsuario();
        }

        private void txtTitulo_TextChanged(object sender, TextChangedEventArgs e)
        {
            FiltrarUsuarios();
        }

        private void txtDirector_TextChanged(object sender, TextChangedEventArgs e)
        {
            FiltrarUsuarios();
        }

        private async Task CargarUsuario()
        {
            ServiceUser serviceUser = new ServiceUser();
            todosUsuarios = await serviceUser.Listar();
            UsuariosVisibles.Clear();
            foreach (var user in todosUsuarios)
            {
                UsuariosVisibles.Add(user);
            }
            FiltrarUsuarios();
        }

        private void FiltrarUsuarios()
        {
            if (todosUsuarios == null) return;

            string filtroNombre = txtTitulo?.Text?.Trim() ?? string.Empty;
            string filtroEmail = txtDirector?.Text?.Trim() ?? string.Empty;

            IEnumerable<User> query = todosUsuarios;

            if (!string.IsNullOrEmpty(filtroNombre))
            {
                query = query.Where(u => !string.IsNullOrEmpty(u.NombreCompleto) &&
                                          u.NombreCompleto.Contains(filtroNombre, StringComparison.OrdinalIgnoreCase));
            }

            if (!string.IsNullOrEmpty(filtroEmail))
            {
                query = query.Where(u => !string.IsNullOrEmpty(u.CorreoElectronico) &&
                                          u.CorreoElectronico.Contains(filtroEmail, StringComparison.OrdinalIgnoreCase));
            }

            
        }

        // Cuando cambia la selección creamos una copia para editar y la enlazamos al panel de detalle.
        private void lbPeliculas_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lbPeliculas.SelectedItem is not User seleccionado)
            {
                _editingUser = null;
                DetailPanel.DataContext = null;
                return;
            }

            // Crear copia superficial (suficiente para este modelo simple)
            _editingUser = new User
            {
                Id = seleccionado.Id,
                Usuario = seleccionado.Usuario,
                PasswordHash = seleccionado.PasswordHash,
                NombreCompleto = seleccionado.NombreCompleto,
                CorreoElectronico = seleccionado.CorreoElectronico,
                Activo = seleccionado.Activo,
                FechaCreacion = seleccionado.FechaCreacion,
                FechaBaja = seleccionado.FechaBaja
            };

            // Asignar la copia al DataContext del panel de detalle para que las ediciones no impacten al objeto original
            DetailPanel.DataContext = _editingUser;
        }

        // Botón Editar: copiar los cambios desde la copia temporal al objeto original y actualizar en BD
        private async void btnEditar_Click(object sender, RoutedEventArgs e)
        {
            if (lbPeliculas.SelectedItem is not User seleccionado)
            {
                MessageBox.Show("Seleccione un usuario para editar.", "Información", MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }

            if (_editingUser == null)
            {
                MessageBox.Show("No hay cambios para guardar.", "Información", MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }

            // Copiar campos editables desde la copia al objeto original antes de enviar a servicio
            seleccionado.Usuario = _editingUser.Usuario;
            seleccionado.NombreCompleto = _editingUser.NombreCompleto;
            seleccionado.CorreoElectronico = _editingUser.CorreoElectronico;
            // No copiamos Activo ni FechaBaja si son solo informativos en este formulario

            try
            {
                ServiceUser service = new ServiceUser();
                bool ok = await service.Actualizar(seleccionado);
                if (ok)
                {
                    MessageBox.Show("Usuario actualizado correctamente.", "Éxito", MessageBoxButton.OK, MessageBoxImage.Information);
                    // Refrescar listas y limpiar la copia
                    FiltrarUsuarios();
                    // Mantener la selección en el mismo índice / usuario
                    DetailPanel.DataContext = null;
                    _editingUser = null;
                }
                else
                {
                    MessageBox.Show("No se pudo actualizar el usuario (no existe).", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al actualizar: " + ex.Message, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        // Botón Borrar: elimina el usuario seleccionado de la base de datos y de la colección
        private async void btnBorrar_Click(object sender, RoutedEventArgs e)
        {
            if (lbPeliculas.SelectedItem is not User seleccionado)
            {
                MessageBox.Show("Seleccione un usuario para borrar.", "Información", MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }

            var res = MessageBox.Show($"¿Desea borrar al usuario '{seleccionado.Usuario}'?", "Confirmar borrado", MessageBoxButton.YesNo, MessageBoxImage.Question);
            if (res != MessageBoxResult.Yes) return;

            try
            {
                ServiceUser  service = new ServiceUser();

                if (seleccionado.Id is int id)
                {
                    bool ok = await service.Borrar(id);
                    if (ok)
                    {
                        UsuariosVisibles.Remove(seleccionado);
                        todosUsuarios.RemoveAll(u => u.Id == seleccionado.Id);
                        lbPeliculas.SelectedIndex = -1;
                        DetailPanel.DataContext = null;
                        _editingUser = null;
                        MessageBox.Show("Usuario borrado correctamente.", "Éxito", MessageBoxButton.OK, MessageBoxImage.Information);
                    }
                    else
                    {
                        MessageBox.Show("No se pudo borrar el usuario (no existe).", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    }
                }
                else
                {
                    MessageBox.Show("El usuario seleccionado no tiene un Id válido.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al borrar: " + ex.Message, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}
